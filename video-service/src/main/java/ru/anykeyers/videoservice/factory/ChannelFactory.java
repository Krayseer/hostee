package ru.anykeyers.videoservice.factory;

import org.springframework.stereotype.Component;
import ru.anykeyers.videoservice.domain.Channel;
import ru.anykeyers.videoservice.domain.User;
import ru.anykeyers.videoservice.domain.dto.CreateChannelDTO;

/**
 * Фабрика для создания канала
 */
@Component
public class ChannelFactory {

    /**
     * Создать канал на основе запроса DTO
     *
     * @param createChannelDTO запрос с данными о канале
     */
    public Channel createChannelFromDTO(CreateChannelDTO createChannelDTO, User user) {
        return Channel.builder()
                .name(createChannelDTO.getName())
                .user(user)
                .description(createChannelDTO.getDescription())
                .build();
    }

}
