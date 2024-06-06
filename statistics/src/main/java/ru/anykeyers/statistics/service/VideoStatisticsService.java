package ru.anykeyers.statistics.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import ru.anykeyers.statistics.processor.BatchProcessor;
import ru.anykeyers.statistics.domain.entity.Video;
import ru.anykeyers.statistics.repository.VideoRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Сервис сбора статистики по видео
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VideoStatisticsService implements BatchProcessor {

    private final Map<Long, AtomicInteger> videoCache = new ConcurrentHashMap<>();

    private final VideoRepository videoRepository;

    /**
     * Обработка просмотра видео
     *
     * @param videoId идентификатор видео
     */
    public void handleWatchVideo(Long videoId) {
        if (!videoCache.containsKey(videoId)) {
            videoCache.put(videoId, new AtomicInteger());
        }
        videoCache.get(videoId).incrementAndGet();
    }

    @Override
    public Runnable process() {
        return () -> {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            videoCache.forEach((videoId, count) -> {
                Video video = videoRepository.findByVideoId(videoId);
                if (video == null) {
                    video = new Video();
                    video.setVideoId(videoId);
                    video.setCountWatches(count.longValue());
                } else {
                    video.setCountWatches(video.getCountWatches() + count.longValue());
                }
                videoRepository.save(video);
            });
            log.info("Process {} video statistics in {} ms", videoCache.size(), stopWatch.getTotalTimeMillis());
            videoCache.clear();
        };
    }

}
