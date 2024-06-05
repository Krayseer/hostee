package ru.anykeyers.storageservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import ru.anykeyers.storageservice.domain.VideoFile;
import ru.anykeyers.storageservice.service.impl.CassandraVideoStorageService;

import java.util.concurrent.*;

/**
 * Сервис кеширования видеороликов в кассандру
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CacheStorageService {

    private final ExecutorService executorService;

    private final CassandraVideoStorageService videoStorageService;

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
        executorService.execute(() -> {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            videoStorageService.saveVideo(videoFile);
            stopWatch.stop();
            log.info("Process videoFile {} in cache in {} ms", videoFile.getFileName(), stopWatch.getTotalTimeMillis());
        });
    }

}
