package ru.anykeyers.videoservice.service;

import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.videoservice.domain.channel.Channel;
import ru.anykeyers.videoservice.domain.channel.CreateChannelDTO;
import ru.krayseer.domain.ChannelDTO;

import java.security.Principal;

/**
 * Сервис для работы с каналами
 */
public interface ChannelService {

    /**
     * Получить канал
     *
     * @param username имя пользователя
     */
    Channel getChannel(String username);

    /**
     * Получить канал
     *
     * @param id идентификатор канала
     */
    ChannelDTO getChannel(Long id);

    /**
     * Зарегистрировать канал
     *
     * @param createChannelDTO данные для регистрации канала
     */
    Channel registerChannel(CreateChannelDTO createChannelDTO, Principal user);

    /**
     * Добавить фотографию каналу
     *
     * @param username  имя пользователя
     * @param photo     фотография
     */
    void addPhoto(String username, MultipartFile photo);

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
