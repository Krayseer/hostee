package ru.anykeyers.storageservice.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import ru.anykeyers.storageservice.VideoCassandraRepository;
import ru.anykeyers.storageservice.domain.Video;
import ru.anykeyers.storageservice.domain.VideoFile;
import ru.anykeyers.storageservice.exception.VideoNotFoundException;

import java.nio.ByteBuffer;
import java.util.Optional;

/**
 * Тесты для сервиса {@link CassandraVideoStorageService}
 */
@ExtendWith(MockitoExtension.class)
class CassandraVideoStorageServiceTest {

    @Mock
    private VideoCassandraRepository videoCassandraRepository;

    @InjectMocks
    private CassandraVideoStorageService cassandraVideoStorageService;

    /**
     * Тест получения видео с несуществующим идентификатором
     */
    @Test
    void getVideoWithIncorrectUUID() {
        String uuid = "test-uuid";
        Mockito.when(videoCassandraRepository.findById(uuid)).thenReturn(Optional.empty());

        VideoNotFoundException videoNotFoundException = Assertions.assertThrows(
                VideoNotFoundException.class, () -> cassandraVideoStorageService.getVideo(uuid)
        );

        Assertions.assertEquals("Video could not be found: test-uuid", videoNotFoundException.getMessage());
    }

    /**
     * Тест успешного получения видео по идентификатору
     */
    @Test
    void getVideoSuccessfully() {
        String uuid = "test-uuid";
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[]{1, 2, 3});
        Video video = new Video(uuid, byteBuffer);
        Mockito.when(videoCassandraRepository.findById(uuid)).thenReturn(Optional.of(video));

        Resource actualVideo = cassandraVideoStorageService.getVideo(uuid);

        Resource expectedVideo = new ByteArrayResource(byteBuffer.array());
        Assertions.assertNotNull(actualVideo);
        Assertions.assertEquals(expectedVideo, actualVideo);
    }

    /**
     * Тест сохранения видео
     */
    @Test
    void saveVideo() {
        VideoFile videoFile = new VideoFile("test-uuid", new byte[]{1, 2, 3});

        String actualUUID = cassandraVideoStorageService.saveVideo(videoFile);

        Mockito.verify(videoCassandraRepository, Mockito.times(1)).save(Mockito.any(Video.class));
        Assertions.assertEquals("test-uuid", actualUUID);
    }

}