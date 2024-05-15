package ru.anykeyers.videoservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.krayseer.domain.dto.statistics.ChannelStatisticsDTO;
import ru.krayseer.domain.dto.statistics.VideoStatisticsDTO;

import java.util.Arrays;

/**
 * Удаленный сервис обработки статистики
 */
@Service
@RequiredArgsConstructor
public class RemoteStatisticsService {

    private static final String URL = "http://localhost:8082/statistics";

    private final RestTemplate restTemplate;

    /**
     * Получить статистику канала
     *
     * @param channelId идентификатор канала
     */
    public ChannelStatisticsDTO getChannelStatistics(Long channelId) {
        return restTemplate.getForObject(URL + "/channel/" + channelId, ChannelStatisticsDTO.class);
    }

    /**
     * Получить статистику для видео
     *
     * @param videoUuid идентификатор видео
     */
    public VideoStatisticsDTO getVideoStatistics(String videoUuid) {
        return restTemplate.getForObject(URL + "/video/" + videoUuid, VideoStatisticsDTO.class);
    }

    /**
     * Получить статистику списка видео
     *
     * @param videoUuids идентификаторы видеороликов
     */
    public VideoStatisticsDTO[] getVideoStatistics(String[] videoUuids) {
        return restTemplate.getForObject(URL + "/video/" + Arrays.toString(videoUuids), VideoStatisticsDTO[].class);
    }

}
