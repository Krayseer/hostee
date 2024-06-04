package ru.anykeyers.statistics.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.krayseer.RemoteConfiguration;
import ru.krayseer.service.RemoteChannelService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Контекст приложения
 */
@Configuration
public class ApplicationContext {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    @Bean
    @ConfigurationProperties(prefix = "remote-configuration")
    public RemoteConfiguration remoteConfiguration() {
        return new RemoteConfiguration();
    }

    @Bean
    public RemoteChannelService remoteChannelService(RestTemplate restTemplate,
                                                     RemoteConfiguration remoteConfiguration) {
        return new RemoteChannelService(restTemplate, remoteConfiguration);
    }

}
