package ru.anykeyers.videoservice.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.anykeyers.videoservice.domain.Channel;
import ru.anykeyers.videoservice.domain.User;
import ru.krayseer.domain.dto.ChannelDTO;
import ru.anykeyers.videoservice.domain.dto.CreateChannelDTO;

/**
 * Фабрика для создания канала
 */
@Component
@RequiredArgsConstructor
public class ChannelFactory {

    private final UserFactory userFactory;

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

    /**
     * Создать DTO с данными о канале
     *
     * @param channel канал
     */
    public ChannelDTO createDTO(Channel channel) {
        return new ChannelDTO(
                channel.getId(),
                userFactory.createUserDTO(channel.getUser()),
                channel.getName(),
                channel.getDescription()
        );
    }

}
