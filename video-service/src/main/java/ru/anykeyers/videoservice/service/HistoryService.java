package ru.anykeyers.videoservice.service;

import ru.anykeyers.videoservice.domain.History;

/**
 * Сервис обработки истории
 */
public interface HistoryService {

    /**
     * Получить историю просмотров пользователя
     *
     * @param username имя пользователя
     */
    History getHistory(String username);

    /**
     * Добавить видео в историю
     *
     * @param username  имя пользователя
     * @param videoUuid идентификатор видео
     */
    void addHistory(String username, String videoUuid);

}
