package ru.anykeyers.videoservice.exception;

/**
 * Исключение, сигнализирующее, что пользователь уже существует
 */
public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String username) {
        super(String.format("User with username '%s' already exists", username));
    }

}
