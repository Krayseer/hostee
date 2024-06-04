package ru.anykeyers.videoservice.service;

import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.videoservice.domain.channel.Channel;
import ru.anykeyers.videoservice.domain.channel.ChannelRequest;
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
    ChannelDTO getChannel(String username);

    /**
     * Получить канал
     *
     * @param id идентификатор канала
     */
    ChannelDTO getChannel(Long id);

    /**
     * Зарегистрировать канал
     *
     * @param channelRequest данные для регистрации канала
     */
    ChannelDTO registerChannel(String username, ChannelRequest channelRequest);

    /**
     * Добавить фотографию каналу
     *
     * @param username  имя пользователя
     * @param photo     фотография
     */
    void addPhoto(String username, MultipartFile photo);

    /**
     * Изменить информацию о канале
     *
     * @param id                идентификатор канала
     * @param channelRequest    данные о канале
     */
    ChannelDTO updateChannel(Long id, ChannelRequest channelRequest);

    /**
     * Удалить канал
     *
     * @param id идентификатор канала
     */
    void deleteChannel(Long id);

}
