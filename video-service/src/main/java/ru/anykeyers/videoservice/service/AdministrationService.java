package ru.anykeyers.videoservice.service;

import ru.anykeyers.videoservice.domain.User;

import java.util.List;

/**
 * Сервис для работы администрирования
 */
public interface AdministrationService {

    /**
     * Получить всех пользователей
     */
    List<User> getAllUsers();

    /**
     * Заблокировать пользователя
     * @param id id пользователя
     * @return заблокированный пользователь
     */
    User blockUser(Long id);

    /**
     * Удалить видео
     * @param uuid uuid видео
     */
    void deleteVideo(String uuid);
}
