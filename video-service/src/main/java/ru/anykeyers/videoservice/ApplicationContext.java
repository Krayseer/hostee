package ru.anykeyers.videoservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;

/**
 * Контекст приложения
 */
@Configuration
public class ApplicationContext {

    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
