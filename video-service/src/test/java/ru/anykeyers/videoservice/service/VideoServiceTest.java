package ru.anykeyers.videoservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.videoservice.AsyncWorker;
import ru.anykeyers.videoservice.domain.channel.Channel;
import ru.anykeyers.videoservice.domain.user.User;
import ru.anykeyers.videoservice.domain.video.UploadStatus;
import ru.anykeyers.videoservice.domain.video.Video;
import ru.anykeyers.videoservice.domain.video.VideoRequest;
import ru.anykeyers.videoservice.exception.ChannelNotExistsException;
import ru.anykeyers.videoservice.repository.ChannelRepository;
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
    private AsyncWorker asyncWorker;

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

    @Captor
    private ArgumentCaptor<Runnable> runnableCaptor;

    /**
     * Тест сохранения информации о видео без отправки в хранилище
     */
    @Test
    void uploadTest() {
        MultipartFile videoFile = Mockito.mock(MultipartFile.class);
        VideoRequest videoRequest = new VideoRequest("name", "description", videoFile);
        User user = User.builder().username("username").build();
        Channel channel = Channel.builder().user(user).build();

        Mockito.when(channelRepository.findChannelByUserUsername("username")).thenReturn(Optional.of(channel));

        try {
            videoService.uploadVideo("username", videoRequest);
        } catch (NullPointerException ignored) {
        }

        Mockito.verify(videoRepository, Mockito.times(1)).save(videoCaptor.capture());
        Video captorVideo = videoCaptor.getValue();
        Assertions.assertEquals("name", captorVideo.getName());
        Assertions.assertEquals("description", captorVideo.getDescription());
        Assertions.assertEquals(channel, captorVideo.getChannel());
        Assertions.assertEquals(UploadStatus.PROCESSING, captorVideo.getUploadStatus());
    }

    /**
     * Тестирование сохранения видео с отправкой на удаленный сервис и получением оттуда идентификатора ресурса асинхронно
     */
    @Test
    void uploadTestWithRemoteStorage() {
        MultipartFile videoFile = new MockMultipartFile("1", "2", "3", new byte[] {1, 2, 3});
        VideoRequest videoRequest = new VideoRequest("name", "description", videoFile);
        User user = User.builder().username("username").build();
        Channel channel = Channel.builder().user(user).build();
        Long videoId = 1L;
        Video video = Video.builder().id(videoId).channel(channel).uploadStatus(UploadStatus.PROCESSING).build();
        String videoResponseUuid = "video-uuid-from-storage";

        Mockito.when(channelRepository.findChannelByUserUsername("username")).thenReturn(Optional.of(channel));
        Mockito.when(videoRepository.save(Mockito.any())).thenReturn(video);
        Mockito.doNothing().when(asyncWorker).addTask(runnableCaptor.capture());
        Mockito.when(remoteStorageService.uploadVideoFile(Mockito.any())).thenReturn(ResponseEntity.of(Optional.of(videoResponseUuid)));
        Mockito.when(videoRepository.findById(videoId)).thenReturn(Optional.of(video));

        videoService.uploadVideo("username", videoRequest);

        Mockito.verify(asyncWorker).addTask(Mockito.any(Runnable.class));

        Runnable task = runnableCaptor.getValue();
        Assertions.assertNotNull(task);
        task.run();

        Mockito.verify(videoRepository).findById(videoId);
        Mockito.verify(videoRepository).save(video);

        Assertions.assertEquals(videoResponseUuid, video.getVideoUuid());
        Assertions.assertEquals(UploadStatus.FINISH, video.getUploadStatus());
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
