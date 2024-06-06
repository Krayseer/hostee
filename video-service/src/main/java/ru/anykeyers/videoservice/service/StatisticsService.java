package ru.anykeyers.videoservice.service;

import ru.anykeyers.videoservice.domain.video.VideoDTO;
import ru.krayseer.domain.statistics.ChannelStatisticsDTO;

import java.util.List;

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
    List<VideoDTO> getUserVideoStatistics(String username);

}
