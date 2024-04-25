package ru.anykeyers.videoservice;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
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
     * Секретный JWT ключ
     */
    private String jwtSecretKey;

    /**
     * Время жизни JWT токена
     */
    private Long jwtTokenLifecycle;

    /**
     * URL сервиса обработки видео
     */
    private String storageServiceUrl;

}
