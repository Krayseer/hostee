package ru.anykeyers.storageservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import ru.anykeyers.storageservice.ApplicationConfig;
import ru.anykeyers.storageservice.domain.VideoFile;

import java.util.Queue;
import java.util.concurrent.*;

/**
 * Сервис кеширования видеороликов в кассандру
 */
@Slf4j
@Service
public class CacheStorageService {

    private final ApplicationConfig applicationConfig;

    private final VideoStorageService videoStorageService;

    private final Queue<VideoFile> filesCache = new LinkedBlockingDeque<>();

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    public CacheStorageService(@Qualifier("cassandraVideoStorageService") VideoStorageService videoStorageService,
                               ApplicationConfig applicationConfig) {
        this.videoStorageService = videoStorageService;
        this.applicationConfig = applicationConfig;
    }

    /**
     * Запуск работы сервиса кеширования видео в хранилище
     */
    public void start() {
        log.info("Starting cache storage service");
        StopWatch stopWatch = new StopWatch();
        scheduler.scheduleAtFixedRate(() -> {
            int cacheSize = filesCache.size();
            stopWatch.start();
            while (!filesCache.isEmpty()) {
                VideoFile file = filesCache.poll();
                if (file == null) {
                    continue;
                }
                executorService.execute(() -> videoStorageService.saveVideo(file));
            }
            stopWatch.stop();
            log.info("Process {} videos from cache storage in {} ms", cacheSize, stopWatch.getTotalTimeMillis());
        }, 0, applicationConfig.getStorageCacheProcessDelayMs(), TimeUnit.MILLISECONDS);
    }

    /**
     * Добавить файл в очередь для дальнейшей отправки в кэш
     *
     * @param videoFile видео-файл
     */
    public void addFile(VideoFile videoFile) {
        log.info("Add video in cache: {}", videoFile.getFileName());
        filesCache.add(videoFile);
    }

}
