package ru.anykeyers.videoservice.service;

import ru.anykeyers.videoservice.domain.Channel;
import ru.anykeyers.videoservice.domain.User;
import ru.anykeyers.videoservice.domain.dto.CreateChannelDTO;

import java.security.Principal;

/**
 * Сервис для работы с каналами
 */
public interface ChannelService {

    /**
     * Получить канал по имени пользователя
     * @param username пользователь
     */
    Channel getChannel(String username);

    /**
     * Зарегистрировать канал
     * @param createChannelDTO данные для регистрации канала
     */
    Channel registerChannel(CreateChannelDTO createChannelDTO, Principal user);

    /**
     * Изменить канал
     *
     * @param channel обновленные данные о канале
     */
    Channel updateChannel(Channel channel);

    /**
     * Удалить канал
     *
     * @param id идентификатор канала
     */
    Channel deleteChannel(Long id);

}
