package ru.anykeyers.videoservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
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

    private final RemoteStorageService remoteStorageService;

    @Override
    public List<VideoDTO> getAllVideo() {
        List<Video> videos = videoRepository.findAll();
        return videos.stream().map(VideoMapper::createDTO).toList();
    }

    @Override
    public Resource getVideo(String uuid) {
        eventService.notifyWatchVideo(uuid);
        return remoteStorageService.getVideoFile(uuid);
    }

    @Override
    public void uploadVideo(String username, VideoRequest videoRequest) {
        Channel channel = channelRepository.findChannelByUserUsername(username).orElseThrow(
                () -> new ChannelNotExistsException(username)
        );
        Video video = VideoMapper.createVideo(videoRequest, channel);
        Video savedVideo = videoRepository.save(video);
        uploadVideo(savedVideo.getId(), videoRequest.getVideo());
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
            Video video = videoRepository.findById(videoId).orElseThrow(
                    () -> new VideoNotFoundException(videoId)
            );
            ResponseEntity<String> videoUuid = remoteStorageService.uploadVideoFile(videoFile);
            video.setVideoUuid(videoUuid.getBody());
            video.setUploadStatus(UploadStatus.FINISH);
            videoRepository.save(video);
        });
    }

}
