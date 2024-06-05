package ru.anykeyers.storageservice.context;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Конфигурация приложения
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "application")
public class ApplicationConfig {

    /**
     * Путь до хранилища
     */
    private String storagePath;

}
