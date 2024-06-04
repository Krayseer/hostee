package ru.anykeyers.videoservice.domain.channel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.anykeyers.videoservice.domain.user.User;
import ru.anykeyers.videoservice.domain.user.UserMapper;
import ru.krayseer.domain.ChannelDTO;

/**
 * Фабрика для создания канала
 */
@Component
@RequiredArgsConstructor
public final class ChannelMapper {

    /**
     * Создать канал на основе запроса DTO
     *
     * @param createChannelDTO запрос с данными о канале
     */
    public static Channel createChannel(CreateChannelDTO createChannelDTO, User user) {
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
    public static ChannelDTO createDTO(Channel channel) {
        return new ChannelDTO(
                channel.getId(),
                UserMapper.createDTO(channel.getUser()),
                channel.getName(),
                channel.getDescription(),
                channel.getPhotoUrl()
        );
    }

}
