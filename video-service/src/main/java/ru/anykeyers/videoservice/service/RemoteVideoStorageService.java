package ru.anykeyers.videoservice.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * Сервис для работы с хранилищем для видео
 */
public interface RemoteVideoStorageService {

    /**
     * Получить видео из хранилища
     *
     * @param uuid идентификатор видео
     */
    Resource getVideoFile(String uuid);

    /**
     * Загрузить видео в хранилище
     *
     * @param video видео
     */
    ResponseEntity<String> uploadVideoFile(MultipartFile video);

}
