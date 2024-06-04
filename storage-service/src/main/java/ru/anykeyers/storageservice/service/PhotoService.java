package ru.anykeyers.storageservice.service;

/**
 * Сервис обработки фотографий
 */
public interface PhotoService {

    /**
     * Загрузить фотографию
     *
     * @param photoBytes фотография в байтах
     */
    String uploadPhoto(byte[] photoBytes);

}
