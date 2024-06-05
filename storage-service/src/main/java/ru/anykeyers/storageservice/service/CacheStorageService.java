package ru.anykeyers.storageservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import ru.anykeyers.storageservice.context.ApplicationConfig;
import ru.anykeyers.storageservice.domain.VideoFile;
import ru.anykeyers.storageservice.service.impl.CassandraVideoStorageService;

import java.util.Queue;
import java.util.concurrent.*;

/**
 * Сервис кеширования видеороликов в кассандру
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CacheStorageService {

    private final ExecutorService executorService;

    private final ScheduledExecutorService scheduler;

    private final ApplicationConfig applicationConfig;

    private final CassandraVideoStorageService videoStorageService;

    private final Queue<VideoFile> filesCache = new LinkedBlockingDeque<>();

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
     * Получить видео
     *
     * @param videoUuid идентификатор видео
     */
    public Resource getVideo(String videoUuid) {
        return videoStorageService.getVideo(videoUuid);
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
