package ru.anykeyers.videoservice.exception;

/**
 * Исключение, сигнализирующее, что пользователя не существует
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String username) {
        super(String.format("User with username '%s' not found", username));
    }

    public UserNotFoundException(Long id) {
        super(String.format("User with id '%d' not found", id));
    }

}
