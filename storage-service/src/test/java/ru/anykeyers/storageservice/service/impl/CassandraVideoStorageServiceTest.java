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
import ru.anykeyers.storageservice.VideoChunkRepository;
import ru.anykeyers.storageservice.domain.VideoFile;

import java.util.Arrays;

/**
 * Тесты для сервиса {@link CassandraVideoStorageService}
 */
@ExtendWith(MockitoExtension.class)
class CassandraVideoStorageServiceTest {

    @Mock
    private VideoChunkRepository videoCassandraRepository;

    @InjectMocks
    private CassandraVideoStorageService cassandraVideoStorageService;

    /**
     * Тест сохранения видео
     */
    @Test
    void saveVideo() {
        int chunkCount = 4;
        int chunkSize = 1024;
        byte[] videoBytes = new byte[chunkSize * chunkSize * chunkCount];
        Arrays.fill(videoBytes, (byte) 0);
        VideoFile videoFile = new VideoFile("2d18790b-2ba5-4581-86da-8032d2869319", videoBytes);

        cassandraVideoStorageService.saveVideo(videoFile);

        Mockito.verify(videoCassandraRepository, Mockito.times(chunkCount)).save(Mockito.any());
    }

}