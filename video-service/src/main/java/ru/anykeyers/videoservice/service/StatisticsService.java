package ru.anykeyers.videoservice.service;

import ru.krayseer.domain.dto.statistics.ChannelStatisticsDTO;
import ru.krayseer.domain.dto.statistics.VideoStatisticsDTO;

/**
 * Сервис сбора статистики
 */
public interface StatisticsService {

    /**
     * Получить статистику канала пользователя
     *
     * @param username имя пользователя
     */
    ChannelStatisticsDTO getUserChannelStatistics(String username);

    /**
     * Получить статистику по видео пользователя
     *
     * @param username имя пользователя
     */
    VideoStatisticsDTO[] getUserVideoStatistics(String username);

}
