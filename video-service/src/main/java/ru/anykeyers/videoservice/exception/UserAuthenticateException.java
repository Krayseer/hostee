package ru.anykeyers.videoservice.exception;

/**
 * Ошибка авторизации пользователя
 */
public class UserAuthenticateException extends RuntimeException {

    public UserAuthenticateException(String username) {
        super(String.format("Authenticate error user: %s", username));
    }

}
