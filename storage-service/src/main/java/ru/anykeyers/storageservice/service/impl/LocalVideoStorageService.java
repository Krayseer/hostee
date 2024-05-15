package ru.anykeyers.storageservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import ru.anykeyers.storageservice.context.ApplicationConfig;
import ru.anykeyers.storageservice.domain.VideoFile;
import ru.anykeyers.storageservice.exception.VideoUploadError;
import ru.anykeyers.storageservice.service.CacheStorageService;
import ru.anykeyers.storageservice.service.VideoStorageService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Реализация сервиса обработки видео с файловой БД
 */
@Slf4j
@RequiredArgsConstructor
@Service("localVideoStorageService")
public class LocalVideoStorageService implements VideoStorageService {

    private final ApplicationConfig applicationConfig;

    private final CacheStorageService cacheStorageService;

    @Override
    public Resource getVideo(String uuid) {
        String storagePath = applicationConfig.getStoragePath();
        Path videoPath = Paths.get(storagePath).resolve(uuid + ".mp4");
        try {
            return new UrlResource(videoPath.toUri());
        } catch (MalformedURLException e) {
            throw new VideoUploadError(uuid);
        }
    }

    @Override
    @SneakyThrows
    public String saveVideo(byte[] content) {
        VideoFile videoFile = new VideoFile(UUID.randomUUID().toString(), content);
        return saveVideo(videoFile);
    }

    @Override
    public String saveVideo(VideoFile videoFile) {
        log.info("Saving video: {}", videoFile.getFileName());
        cacheStorageService.addFile(videoFile);
        try {
            Path videoPath = Path.of(applicationConfig.getStoragePath() + videoFile.getFileName() + ".mp4");
            Files.write(videoPath.toFile().toPath(), videoFile.getContent());
        } catch (IOException ex) {
            throw new VideoUploadError(videoFile.getFileName());
        }
        return videoFile.getFileName();
    }

}
