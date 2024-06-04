package ru.anykeyers.videoservice.exception;

/**
 * Исключение о несуществовании канала
 */
public class ChannelNotExistsException extends RuntimeException {

    public ChannelNotExistsException(String username) {
        super(String.format("Channel not exists for user: %s", username));
    }

    public ChannelNotExistsException(Long id) {
        super(String.format("Channel not exists with id: %d", id));
    }

}
