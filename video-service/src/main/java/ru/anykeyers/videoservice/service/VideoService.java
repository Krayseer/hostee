package ru.anykeyers.videoservice.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.videoservice.domain.dto.UploadVideoDTO;

/**
 * Сервис для работы с видео
 */
public interface VideoService {

    /**
     * Получить видео из хранилища по его id
     *
     * @param uuid id видео в хранилище
     */
    Resource getVideo(String uuid);

    /**
     * Загрузить видео в хранилище
     *
     * @param uploadVideoDTO    данные DTO
     * @param video             видео
     * @param username          имя пользователя
     */
    void uploadVideo(UploadVideoDTO uploadVideoDTO, MultipartFile video, String username);

    /**
     * Удалить видео
     *
     * @param uuid идентификатор видео
     */
    void deleteVideo(String uuid);

}
