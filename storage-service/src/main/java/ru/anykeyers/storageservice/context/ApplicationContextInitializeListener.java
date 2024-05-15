package ru.anykeyers.storageservice.context;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ru.anykeyers.storageservice.service.CacheStorageService;

/**
 * Слушатель события загрузки приложения
 */
@Component
@RequiredArgsConstructor
public class ApplicationContextInitializeListener implements ApplicationListener<ContextRefreshedEvent> {

    private final CacheStorageService cacheStorageService;

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        cacheStorageService.start();
    }

}
