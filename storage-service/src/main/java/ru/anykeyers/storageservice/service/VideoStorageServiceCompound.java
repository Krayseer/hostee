package ru.anykeyers.storageservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.anykeyers.storageservice.domain.VideoFile;
import ru.anykeyers.storageservice.service.impl.CassandraVideoStorageService;
import ru.anykeyers.storageservice.service.impl.LocalVideoStorageService;

import java.util.UUID;

/**
 * Комплексная обработка сервисов по обработке видео
 */
@Service
@RequiredArgsConstructor
public class VideoStorageServiceCompound {

    private final CacheStorageService cacheStorageService;

    private final LocalVideoStorageService localVideoStorageService;

    private final CassandraVideoStorageService cassandraVideoStorageService;

    /**
     * Получить видео
     *
     * @param uuid идентификатор видео
     */
    public Resource getVideo(String uuid) {
        try {
            return localVideoStorageService.getVideo(uuid);
        } catch (Exception ex) {
            return cassandraVideoStorageService.getVideo(uuid);
        }
    }

    /**
     * Сохранить видео
     *
     * @param bytes видео в байтовом представлении
     */
    public String saveVideo(byte[] bytes) {
        VideoFile videoFile = new VideoFile(generateUniqueName(), bytes);
        localVideoStorageService.saveVideo(videoFile);
        cacheStorageService.addFile(videoFile);
        return videoFile.getFileName();
    }

    private String generateUniqueName() {
        return UUID.randomUUID().toString();
    }

}
