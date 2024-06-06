package ru.anykeyers.videoservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.videoservice.AsyncWorker;
import ru.anykeyers.videoservice.domain.channel.Channel;
import ru.anykeyers.videoservice.domain.video.*;
import ru.anykeyers.videoservice.exception.ChannelNotExistsException;
import ru.anykeyers.videoservice.exception.VideoNotFoundException;
import ru.anykeyers.videoservice.repository.ChannelRepository;
import ru.anykeyers.videoservice.repository.VideoRepository;
import ru.anykeyers.videoservice.service.EventService;
import ru.anykeyers.videoservice.service.StatisticsService;
import ru.anykeyers.videoservice.service.VideoService;
import ru.krayseer.service.RemoteStorageService;

import java.util.List;

/**
 * Реализация сервиса для работы с видео
 */
@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final AsyncWorker worker;

    private final EventService eventService;

    private final VideoRepository videoRepository;

    private final ChannelRepository channelRepository;

    private final StatisticsService statisticsService;

    private final RemoteStorageService remoteStorageService;

    @Override
    public List<VideoDTO> getAllVideo() {
        List<Video> videos = videoRepository.findAll();
        return statisticsService.getVideoStatistics(videos);
    }

    @Override
    public List<VideoDTO> getVideos(String username) {
        Channel channel = channelRepository.findChannelByUserUsername(username).orElseThrow(
                () -> new ChannelNotExistsException(username)
        );;
        List<Video> videos = videoRepository.findByChannel(channel);
        return videos.stream().map(VideoMapper::createDTO).toList();
    }

    @Override
    public Resource getVideo(Long id) {
        Video video = videoRepository.findById(id).orElseThrow(
                () -> new VideoNotFoundException(id)
        );
        eventService.notifyWatchVideo(id);
        return remoteStorageService.getVideoFile(video.getVideoUuid());
    }

    @Override
    public void uploadVideo(String username, VideoRequest videoRequest) {
        Channel channel = channelRepository.findChannelByUserUsername(username).orElseThrow(
                () -> new ChannelNotExistsException(username)
        );
        Video video = VideoMapper.createVideo(videoRequest, channel);
        Video savedVideo = videoRepository.save(video);
        uploadVideo(savedVideo.getId(), copyMultipartFile(videoRequest.getVideo()));
        if (videoRequest.getPreview() != null) {
            uploadPhoto(savedVideo.getId(), copyMultipartFile(videoRequest.getPreview()));
        }
    }

    @Override
    public void deleteVideo(String uuid) {
        videoRepository.deleteByVideoUuid(uuid);
    }

    /**
     * Загрузить видео в удаленное хранилище
     *
     * @param videoId   идентификатор видео
     * @param videoFile файл видео
     */
    private void uploadVideo(Long videoId, MultipartFile videoFile) {
        worker.addTask(() -> {
            ResponseEntity<String> videoUuid = remoteStorageService.uploadVideoFile(videoFile);
            Video video = videoRepository.findById(videoId).orElseThrow(
                    () -> new VideoNotFoundException(videoId)
            );
            video.setVideoUuid(videoUuid.getBody());
            video.setUploadStatus(UploadStatus.FINISH);
            videoRepository.save(video);
        });
    }

    /**
     * Загрузить превью в удаленное хранилище
     *
     * @param videoId   идентификатор видео
     * @param preview   фотография - превью
     */
    private void uploadPhoto(Long videoId, MultipartFile preview) {
        worker.addTask(() -> {
            ResponseEntity<String> photoUuid = remoteStorageService.uploadPhoto(preview);
            Video video = videoRepository.findById(videoId).orElseThrow(
                    () -> new VideoNotFoundException(videoId)
            );
            video.setPreviewUuid(photoUuid.getBody());
            videoRepository.save(video);
        });
    }

    @SneakyThrows
    private MultipartFile copyMultipartFile(MultipartFile original) {
        return new MockMultipartFile(
                original.getName(),
                original.getOriginalFilename(),
                original.getContentType(),
                original.getBytes());
    }

}
