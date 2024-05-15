package ru.anykeyers.videoservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.videoservice.domain.Channel;
import ru.anykeyers.videoservice.domain.User;
import ru.anykeyers.videoservice.domain.Video;
import ru.anykeyers.videoservice.domain.dto.UploadVideoDTO;
import ru.anykeyers.videoservice.factory.VideoFactory;
import ru.anykeyers.videoservice.repository.ChannelRepository;
import ru.anykeyers.videoservice.repository.UserRepository;
import ru.anykeyers.videoservice.repository.VideoRepository;
import ru.anykeyers.videoservice.service.impl.VideoServiceImpl;
import ru.anykeyers.videoservice.service.remote.RemoteStorageService;

import static org.mockito.Mockito.*;

/**
 * Тесты для сервиса {@link VideoService}
 */
@ExtendWith(MockitoExtension.class)
public class VideoServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ChannelRepository channelRepository;

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private RemoteStorageService remoteStorageService;

    @Mock
    private VideoFactory videoFactory;

    @InjectMocks
    private VideoServiceImpl videoService;

    /**
     * Тест успешной загрузки видео
     */
    @Test
    void uploadTest() {
        UploadVideoDTO uploadVideoDTO = new UploadVideoDTO();
        MultipartFile mockMultipartFile = mock(MultipartFile.class);
        User mockUser = new User();
        Channel mockChannel = new Channel();
        Video mockVideo = new Video();
        ResponseEntity<String> mockResponseEntity = ResponseEntity.ok("video_uuid");

        when(userRepository.findByUsername(anyString())).thenReturn(mockUser);
        when(channelRepository.findChannelByUser(any(User.class))).thenReturn(mockChannel);
        when(remoteStorageService.uploadVideoFile(any(MultipartFile.class))).thenReturn(mockResponseEntity);
        when(videoFactory.createVideoFromDto(any(UploadVideoDTO.class), any(Channel.class))).thenReturn(mockVideo);

        videoService.uploadVideo(uploadVideoDTO, mockMultipartFile, "username");

        verify(userRepository, times(1)).findByUsername("username");
        verify(channelRepository, times(1)).findChannelByUser(mockUser);
        verify(remoteStorageService, times(1)).uploadVideoFile(mockMultipartFile);
        verify(videoFactory, times(1)).createVideoFromDto(uploadVideoDTO, mockChannel);
        verify(videoRepository, times(1)).save(mockVideo);
    }

    /**
     * Тест загрузки видео с ошибкой
     */
    @Test
    void badUploadTest() {
        UploadVideoDTO uploadVideoDTO = new UploadVideoDTO();
        MultipartFile mockMultipartFile = mock(MultipartFile.class);
        User mockUser = new User();
        Channel mockChannel = null;
        Video mockVideo = new Video();
        ResponseEntity<String> mockResponseEntity = ResponseEntity.ok("video_uuid");

        when(userRepository.findByUsername(anyString())).thenReturn(mockUser);

        RuntimeException runtimeException = Assertions.assertThrows(
                RuntimeException.class, () -> videoService.uploadVideo(uploadVideoDTO, mockMultipartFile, "username")
        );

        verify(userRepository, times(1)).findByUsername("username");
        verify(channelRepository, times(1)).findChannelByUser(mockUser);
    }

    /**
     * Тест метода получения видео
     */
    @Test
    void getVideoTest() {
        String uuid = "video_uuid";
        Resource mockResource = mock(Resource.class);

        when(remoteStorageService.getVideoFile(anyString())).thenReturn(mockResource);

        Resource result = videoService.getVideo(uuid);

        verify(remoteStorageService, times(1)).getVideoFile(uuid);
        Assertions.assertEquals(mockResource, result);
    }

}
