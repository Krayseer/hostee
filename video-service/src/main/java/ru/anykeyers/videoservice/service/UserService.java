package ru.anykeyers.videoservice.service;

import ru.anykeyers.videoservice.domain.dto.AuthDTO;
import ru.anykeyers.videoservice.domain.user.RegisterDTO;
import ru.anykeyers.videoservice.domain.dto.TokenDTO;
import ru.anykeyers.videoservice.domain.user.Role;
import ru.krayseer.domain.UserSettingDTO;
import ru.krayseer.domain.UserDTO;

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
     * Получить список всех пользователей
     */
    List<UserDTO> getAllUsers();

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
     * @param userSettingDTO    данные о настройках уведомлений
     */
    void setNotificationSetting(String username, UserSettingDTO userSettingDTO);

    /**
     * Установить пользователю роли
     *
     * @param username  имя пользователя
     * @param roles     список ролей
     */
    void setUserRoles(String username, List<Role> roles);

    /**
     * Заблокировать пользователя
     *
     * @param id идентификатор пользователя
     */
    UserDTO blockUser(Long id);

    /**
     * Разблокировать пользователя
     *
     * @param id идентификатор пользователя
     */
    UserDTO unblockUser(Long id);
}
