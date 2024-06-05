package ru.krayseer.service;

import org.springframework.web.client.RestTemplate;
import ru.krayseer.RemoteConfiguration;
import ru.krayseer.domain.statistics.ChannelStatisticsDTO;
import ru.krayseer.domain.statistics.VideoStatisticsDTO;

import java.util.Arrays;

/**
 * Удаленный сервис обработки статистики
 */
public class RemoteStatisticsService {

    private final String URL;

    private final RestTemplate restTemplate;

    public RemoteStatisticsService(RestTemplate restTemplate,
                                   RemoteConfiguration remoteConfiguration) {
        this.restTemplate = restTemplate;
        this.URL = remoteConfiguration.getStatisticsUrl() + "/statistics";
    }

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