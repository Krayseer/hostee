package ru.anykeyers.videoservice.exception;

/**
 * Исключение, что канал уже существует
 */
public class ChannelAlreadyExistsException extends RuntimeException {

    public ChannelAlreadyExistsException(String username) {
        super(String.format("Channel already exists for user: %s", username));
    }

}
