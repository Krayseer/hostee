package ru.anykeyers.storageservice.exception;

/**
 * Исключение, которое сигнализирует, что видео не существует
 */
public class VideoNotFoundException extends RuntimeException {

    public VideoNotFoundException(String videoUUID) {
        super(String.format("Video could not be found: %s", videoUUID));
    }

}
