package ru.anykeyers.videoservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.videoservice.domain.Channel;
import ru.anykeyers.videoservice.domain.User;
import ru.anykeyers.videoservice.domain.Video;
import ru.anykeyers.videoservice.domain.dto.UploadVideoDTO;
import ru.anykeyers.videoservice.factory.VideoFactory;
import ru.anykeyers.videoservice.repository.ChannelRepository;
import ru.anykeyers.videoservice.repository.UserRepository;
import ru.anykeyers.videoservice.repository.VideoRepository;
import ru.anykeyers.videoservice.service.VideoService;

/**
 * Реализация сервиса для работы с видео
 */
@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;

    private final RemoteVideoStorageServiceImpl remoteVideoStorageServiceImpl;

    private final UserRepository userRepository;

    private final VideoFactory videoFactory;

    private final ChannelRepository channelRepository;

    @Override
    public void uploadVideo(UploadVideoDTO uploadVideoDTO, MultipartFile video, String username) {
        User user = userRepository.findByUsername(username);
        Channel channel = channelRepository.findChannelByUser(user);
        if (channel == null) {
            throw new RuntimeException("channel doesn't exist");
        }
        ResponseEntity<String> video_uuid = remoteVideoStorageServiceImpl.uploadVideoFile(video);
        Video currentVideo = videoFactory.createVideoFromDto(uploadVideoDTO, channel);
        currentVideo.setVideoUuid(video_uuid.getBody());
        videoRepository.save(currentVideo);
    }

    @Override
    public Resource getVideo(String uuid) {
        return remoteVideoStorageServiceImpl.getVideoFile(uuid);
    }

}
