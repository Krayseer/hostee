package ru.anykeyers.storageservice.exception;

/**
 * Исключение, сигнализирующее, что не удалось загрузить видео
 */
public class VideoUploadError extends RuntimeException {

    public VideoUploadError(String videoUUID) {
        super(String.format("Error video upload: %s", videoUUID));
    }

}
