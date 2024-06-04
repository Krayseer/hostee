package ru.krayseer;

import lombok.Getter;
import lombok.Setter;

/**
 * Конфигурация удаленных сервисов
 */
@Getter
@Setter
public class RemoteConfiguration {

    /**
     * URL сервиса обработки видео
     */
    private String videoServiceUrl;

    /**
     * URL сервиса хранилища
     */
    private String storageServiceUrl;

    /**
     * URL статистики
     */
    private String statisticsUrl;

}
