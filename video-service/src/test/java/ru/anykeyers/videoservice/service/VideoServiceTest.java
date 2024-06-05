package ru.anykeyers.videoservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.videoservice.domain.channel.Channel;
import ru.anykeyers.videoservice.domain.user.User;
import ru.anykeyers.videoservice.domain.video.Video;
import ru.anykeyers.videoservice.domain.video.VideoRequest;
import ru.anykeyers.videoservice.exception.ChannelNotExistsException;
import ru.anykeyers.videoservice.repository.ChannelRepository;
import ru.anykeyers.videoservice.repository.UserRepository;
import ru.anykeyers.videoservice.repository.VideoRepository;
import ru.anykeyers.videoservice.service.impl.VideoServiceImpl;
import ru.krayseer.service.RemoteStorageService;

import java.util.Optional;

/**
 * Тесты для сервиса {@link VideoService}
 */
@ExtendWith(MockitoExtension.class)
public class VideoServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private ChannelRepository channelRepository;

    @Mock
    private RemoteStorageService remoteStorageService;

    @InjectMocks
    private VideoServiceImpl videoService;

    @Captor
    private ArgumentCaptor<Video> videoCaptor;

    /**
     * Тест успешной загрузки видео
     */
    @Test
    void uploadTest() {
        MultipartFile videoFile = Mockito.mock(MultipartFile.class);
        VideoRequest videoRequest = new VideoRequest("name", "description", videoFile);
        User user = User.builder().username("username").build();
        Channel channel = Channel.builder().user(user).build();

        Mockito.when(channelRepository.findChannelByUserUsername("username")).thenReturn(Optional.of(channel));
        Mockito.when(remoteStorageService.uploadVideoFile(Mockito.any(MultipartFile.class))).thenReturn(ResponseEntity.ok("video_uuid"));

        videoService.uploadVideo("username", videoRequest);

        Mockito.verify(remoteStorageService, Mockito.times(1)).uploadVideoFile(videoFile);
        Mockito.verify(videoRepository, Mockito.times(1)).save(videoCaptor.capture());
        Video captorVideo = videoCaptor.getValue();
        Assertions.assertEquals(captorVideo.getVideoUuid(), "video_uuid");
    }

    /**
     * Тест загрузки видео с ошибкой
     */
    @Test
    void badUploadTest() {
        Mockito.when(channelRepository.findChannelByUserUsername("username")).thenReturn(Optional.empty());

        ChannelNotExistsException exception = Assertions.assertThrows(
                ChannelNotExistsException.class, () -> videoService.uploadVideo("username", Mockito.mock(VideoRequest.class))
        );

        Assertions.assertEquals("Channel not exists for user: username", exception.getMessage());
    }

}
