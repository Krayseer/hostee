package ru.anykeyers.videoservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import ru.krayseer.RemoteConfiguration;
import ru.krayseer.service.RemoteStatisticsService;
import ru.krayseer.service.RemoteStorageService;

/**
 * Контекст приложения
 */
@EnableAsync
@Configuration
public class ApplicationContext {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    @ConfigurationProperties(prefix = "remote-configuration")
    public RemoteConfiguration remoteConfiguration() {
        return new RemoteConfiguration();
    }

    @Bean
    public RemoteStatisticsService remoteStatisticsService(RestTemplate restTemplate,
                                                           RemoteConfiguration remoteConfiguration) {
        return new RemoteStatisticsService(restTemplate, remoteConfiguration);
    }

    @Bean
    public RemoteStorageService remoteStorageService(RestTemplate restTemplate,
                                                     RemoteConfiguration remoteConfiguration) {
        return new RemoteStorageService(restTemplate, remoteConfiguration);
    }

}
