package ru.anykeyers.storageservice.context;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ObjectStorageConfig {

    /**
     * Название бакета хранилища (передается через docker-compose.yml)
     */
    @Value("${BUCKET_NAME}")
    private String bucketName;

    /**
     * Ключ доступа (передается через docker-compose.yml)
     */
    @Value("${ACCESS_KEY_ID}")
    private String accessKeyId;

    /**
     * Секретный ключ (передается через docker-compose.yml)
     */
    @Value("${SECRET_KEY}")
    private String secretAccessKey;

}
