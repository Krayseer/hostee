package ru.anykeyers.notificationservice.service;

import ru.krayseer.domain.dto.UserDTO;

/**
 * Сервис отправки уведомлений
 */
public interface NotificationService {

    /**
     * Уведомить пользователя
     *
     * @param user      пользователь
     * @param message   сообщение
     */
    void notify(UserDTO user, Object message);

}
