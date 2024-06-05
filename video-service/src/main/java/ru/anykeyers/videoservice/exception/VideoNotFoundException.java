package ru.anykeyers.videoservice.exception;

/**
 * Исключение, что не существует видеоролик
 */
public class VideoNotFoundException extends RuntimeException {

    public VideoNotFoundException(Long id) {
        super("Video with id " + id + " not found");
    }

}
