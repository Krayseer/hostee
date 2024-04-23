package ru.anykeyers.storageservice.service;

import org.springframework.core.io.Resource;
import ru.anykeyers.storageservice.domain.VideoFile;

/**
 * Сервис обработки видео
 */
public interface VideoStorageService {

    /**
     * Получить видео
     *
     * @param name название видео
     */
    Resource getVideo(String name);

    /**
     * Сохранить видео
     *
     * @param content контент видео в байтах
     * @return идентификатор видео
     */
    String saveVideo(byte[] content);

    /**
     * Сохранить видео
     *
     * @param videoFile данные о видео-файле (id, content)
     */
    String saveVideo(VideoFile videoFile);

}
