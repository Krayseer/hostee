package ru.anykeyers.videoservice.service;

import ru.anykeyers.videoservice.domain.User;
import ru.anykeyers.videoservice.domain.dto.AuthDTO;
import ru.anykeyers.videoservice.domain.dto.RegisterDTO;
import ru.anykeyers.videoservice.domain.dto.TokenDTO;

/**
 * Сервис для работы с пользователями
 */
public interface UserService {

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

    User getUser(String username);

}
