package ru.anykeyers.videoservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.anykeyers.videoservice.domain.channel.Channel;
import ru.anykeyers.videoservice.domain.user.User;
import ru.anykeyers.videoservice.domain.video.Video;
import ru.anykeyers.videoservice.domain.video.VideoDTO;
import ru.anykeyers.videoservice.domain.video.VideoRequest;
import ru.anykeyers.videoservice.domain.video.VideoMapper;
import ru.anykeyers.videoservice.repository.ChannelRepository;
import ru.anykeyers.videoservice.repository.UserRepository;
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

    private final EventService eventService;

    private final UserRepository userRepository;

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
        User user = userRepository.findByUsername(username);
        Channel channel = channelRepository.findChannelByUser(user);
        if (channel == null) {
            throw new RuntimeException("channel doesn't exist");
        }
        ResponseEntity<String> video_uuid = remoteStorageService.uploadVideoFile(videoRequest.getVideo());
        Video video = VideoMapper.createVideo(videoRequest, channel);
        video.setVideoUuid(video_uuid.getBody());
        videoRepository.save(video);
    }

    @Override
    public void deleteVideo(String uuid) {
        videoRepository.deleteByVideoUuid(uuid);
    }

}
