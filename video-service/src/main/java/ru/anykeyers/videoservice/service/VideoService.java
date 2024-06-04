package ru.anykeyers.videoservice.service;

import org.springframework.core.io.Resource;
import ru.anykeyers.videoservice.domain.video.VideoDTO;
import ru.anykeyers.videoservice.domain.video.VideoRequest;

import java.util.List;

/**
 * Сервис для работы с видео
 */
public interface VideoService {

    /**
     * Получить список видео
     */
    List<VideoDTO> getAllVideo();

    /**
     * Получить видео из хранилища по его id
     *
     * @param uuid id видео в хранилище
     */
    Resource getVideo(String uuid);

    /**
     * Загрузить видео в хранилище
     *
     * @param username  имя пользователя
     * @param videoDTO  данные о видео
     */
    void uploadVideo(String username, VideoRequest videoDTO);

    /**
     * Удалить видео
     *
     * @param uuid идентификатор видео
     */
    void deleteVideo(String uuid);

}
