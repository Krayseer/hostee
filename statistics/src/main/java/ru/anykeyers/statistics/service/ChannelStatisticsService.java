package ru.anykeyers.statistics.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import ru.anykeyers.statistics.domain.entity.Channel;
import ru.anykeyers.statistics.domain.ChannelStatistics;
import ru.anykeyers.statistics.processor.BatchProcessor;
import ru.anykeyers.statistics.repository.ChannelRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Сервис обработки статистики по каналам
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChannelStatisticsService implements BatchProcessor {

    private final Map<Long, ChannelStatistics> channelStatisticsMap = new ConcurrentHashMap<>();

    private final ChannelRepository channelRepository;

    /**
     * Обработка просмотра страницы канала
     *
     * @param channelId идентификатор канала
     */
    public void handleWatchChannel(Long channelId) {
        getOrCreateChannelStatistics(channelId).addCountWatches();
    }

    /**
     * Обработка подписки на канал
     *
     * @param channelId идентификатор канала
     */
    public void handleSubscribeChannel(Long channelId) {
        getOrCreateChannelStatistics(channelId).addSubscriber();
    }

    private ChannelStatistics getOrCreateChannelStatistics(Long channelId) {
        if (!channelStatisticsMap.containsKey(channelId)) {
            channelStatisticsMap.put(channelId, new ChannelStatistics());
        }
        return channelStatisticsMap.get(channelId);
    }

    @Override
    public Runnable process() {
        return () -> {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            channelStatisticsMap.forEach((channelId, statistics) -> {
                Channel channel = channelRepository.findById(channelId).orElse(null);
                long countWatches = channelStatisticsMap.get(channelId).getCountWatches().longValue();
                long subscribersCount = channelStatisticsMap.get(channelId).getSubscriberCount().longValue();
                if (channel == null) {
                    channel = new Channel();
                    channel.setId(channelId);
                    channel.setCountWatches(countWatches);
                    channel.setSubscribersCount(subscribersCount);
                } else {
                    channel.setCountWatches(channel.getCountWatches() + countWatches);
                    channel.setSubscribersCount(channel.getSubscribersCount() + subscribersCount);
                }
                channelRepository.save(channel);
            });
            log.info("Process {} channels statistics in {} ms",
                    channelStatisticsMap.size(), stopWatch.getTotalTimeMillis());
            channelStatisticsMap.clear();
        };
    }

}
