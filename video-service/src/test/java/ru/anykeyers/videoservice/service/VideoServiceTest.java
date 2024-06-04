//package ru.anykeyers.videoservice.service;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.*;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.multipart.MultipartFile;
//import ru.anykeyers.videoservice.domain.channel.Channel;
//import ru.anykeyers.videoservice.domain.user.User;
//import ru.anykeyers.videoservice.domain.video.Video;
//import ru.anykeyers.videoservice.domain.video.VideoRequest;
//import ru.anykeyers.videoservice.repository.ChannelRepository;
//import ru.anykeyers.videoservice.repository.UserRepository;
//import ru.anykeyers.videoservice.repository.VideoRepository;
//import ru.anykeyers.videoservice.service.impl.VideoServiceImpl;
//import ru.krayseer.service.RemoteStorageService;
//
///**
// * Тесты для сервиса {@link VideoService}
// */
//@ExtendWith(MockitoExtension.class)
//public class VideoServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private VideoRepository videoRepository;
//
//    @Mock
//    private ChannelRepository channelRepository;
//
//    @Mock
//    private RemoteStorageService remoteStorageService;
//
//    @InjectMocks
//    private VideoServiceImpl videoService;
//
//    @Captor
//    private ArgumentCaptor<Video> videoCaptor;
//
//    /**
//     * Тест успешной загрузки видео
//     */
//    @Test
//    void uploadTest() {
//        VideoRequest videoRequest = new VideoRequest();
//        MultipartFile mockMultipartFile = Mockito.mock(MultipartFile.class);
//        User user = new User();
//        Channel channel = new Channel();
//        Video video = new Video();
//
//        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
//        Mockito.when(channelRepository.findChannelByUser(Mockito.any(User.class))).thenReturn(channel);
//        Mockito.when(remoteStorageService.uploadVideoFile(Mockito.any(MultipartFile.class))).thenReturn(ResponseEntity.ok("video_uuid"));
//
//        videoService.uploadVideo(videoRequest, mockMultipartFile, "username");
//
//        Mockito.verify(remoteStorageService, Mockito.times(1)).uploadVideoFile(mockMultipartFile);
//        Mockito.verify(videoRepository, Mockito.times(1)).save(videoCaptor.capture());
//
//        Video captorVideo = videoCaptor.getValue();
//        Assertions.assertEquals(captorVideo.getVideoUuid(), "video_uuid");
//    }
//
//    /**
//     * Тест загрузки видео с ошибкой
//     */
//    @Test
//    void badUploadTest() {
//        VideoRequest videoRequest = new VideoRequest();
//        MultipartFile mockMultipartFile = Mockito.mock(MultipartFile.class);
//        User mockUser = new User();
//        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(mockUser);
//
//        Assertions.assertThrows(
//                RuntimeException.class, () -> videoService.uploadVideo(videoRequest, mockMultipartFile, "username")
//        );
//
//        Mockito.verify(userRepository, Mockito.times(1)).findByUsername("username");
//        Mockito.verify(channelRepository, Mockito.times(1)).findChannelByUser(mockUser);
//    }
//
//}
