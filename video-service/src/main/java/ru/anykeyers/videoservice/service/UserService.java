package ru.anykeyers.videoservice.service;

import ru.anykeyers.videoservice.domain.dto.AuthDTO;
import ru.anykeyers.videoservice.domain.dto.RegisterDTO;
import ru.anykeyers.videoservice.domain.dto.TokenDTO;
import ru.anykeyers.videoservice.domain.Role;
import ru.krayseer.domain.dto.NotificationSettingDTO;
import ru.krayseer.domain.dto.UserDTO;

import java.util.List;

/**
 * Сервис для работы с пользователями
 */
public interface UserService {

    /**
     * Получить данные о пользователе
     *
     * @param username имя пользователя
     */
    UserDTO getUser(String username);

    /**
     * Зарегистрировать пользователя
     *
     * @param registerDTO данные для регистрации пользователя
     * @return JWT токен пользователя
     */
    TokenDTO registerUser(RegisterDTO registerDTO);

    /**
     * Авторизовать пользователя
     *
     * @param authDTO данные для авторизации пользователя
     * @return JWT токен пользователя
     */
    TokenDTO authUser(AuthDTO authDTO);

    /**
     * Установить настройки уведомлений
     *
     * @param username                  имя пользователя
     * @param notificationSettingDTO    данные о настройках уведомлений
     */
    void setNotificationSetting(String username, NotificationSettingDTO notificationSettingDTO);

    /**
     * Установить пользователю роли
     *
     * @param username  имя пользователя
     * @param roles     список ролей
     */
    void setUserRoles(String username, List<Role> roles);

}
