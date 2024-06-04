package ru.anykeyers.videoservice.config;

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
@ConfigurationProperties(prefix = "jwt-config")
public class JwtConfig {

    /**
     * Секретный JWT ключ
     */
    private String jwtSecretKey;

    /**
     * Время жизни JWT токена
     */
    private Long jwtTokenLifecycle;

}
