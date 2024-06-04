package ru.krayseer.service;

import org.springframework.web.client.RestTemplate;
import ru.krayseer.RemoteConfiguration;
import ru.krayseer.domain.ChannelDTO;

public class RemoteChannelService {

    private final String URL;

    private final RestTemplate restTemplate;

    public RemoteChannelService(RestTemplate restTemplate,
                                RemoteConfiguration remoteConfiguration) {
        this.restTemplate = restTemplate;
        this.URL = remoteConfiguration.getVideoServiceUrl() + "/channel";
    }

    /**
     * Получить данные о канале
     *
     * @param channelId идентификатор канала
     */
    public ChannelDTO getChannel(String channelId) {
        return restTemplate.getForObject(URL + "/" + channelId, ChannelDTO.class);
    }

}
