package ru.krayseer.service;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.krayseer.RemoteConfiguration;
import ru.krayseer.domain.statistics.ChannelStatisticsDTO;
import ru.krayseer.domain.statistics.VideoStatisticsDTO;

import java.util.Arrays;
import java.util.Collections;

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
     * @param videoId идентификатор видео
     */
    public VideoStatisticsDTO getVideoStatistics(String videoId) {
        return restTemplate.getForObject(URL + "/video/" + videoId, VideoStatisticsDTO.class);
    }

    /**
     * Получить статистику списка видео
     *
     * @param videoIds идентификаторы видеороликов
     */
    public VideoStatisticsDTO[] getVideoStatistics(String[] videoIds) {
        String url = UriComponentsBuilder
                .fromHttpUrl(URL + "/video")
                .queryParam("videoIds", Arrays.stream(videoIds).toArray())
                .encode()
                .toUriString();
        return restTemplate.getForObject(url, VideoStatisticsDTO[].class);
    }

}
