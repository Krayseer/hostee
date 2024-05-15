package ru.anykeyers.storageservice.service.impl;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.anykeyers.storageservice.context.ApplicationConfig;
import ru.anykeyers.storageservice.domain.VideoFile;
import ru.anykeyers.storageservice.service.CacheStorageService;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Тесты для сервиса {@link LocalVideoStorageService}
 */
@ExtendWith(MockitoExtension.class)
class LocalVideoStorageServiceTest {

    @Mock
    private ApplicationConfig applicationConfig;

    @Mock
    private CacheStorageService cacheStorageService;

    @InjectMocks
    private LocalVideoStorageService localVideoStorageService;

    /**
     * Тест успешного сохранения видео в файловом хранилище с передачей видео в кэш
     *
     * @param tempDir временная папка
     */
    @Test
    @SneakyThrows
    void saveVideo(@TempDir Path tempDir) {
        VideoFile videoFile = new VideoFile("test-uuid", new byte[]{1,2,3,});
        String storagePath = tempDir.toString() + "\\";
        Mockito.when(applicationConfig.getStoragePath()).thenReturn(storagePath);

        String uuid = localVideoStorageService.saveVideo(videoFile);

        Mockito.verify(cacheStorageService, Mockito.times(1)).addFile(videoFile);
        byte[] actualFileBytes = Files.readAllBytes(Path.of(storagePath + "test-uuid.mp4"));
        Assertions.assertArrayEquals(new byte[]{1, 2, 3}, actualFileBytes);
        Assertions.assertEquals("test-uuid", uuid);
    }

}