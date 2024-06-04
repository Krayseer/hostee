package ru.anykeyers.videoservice.service;

import ru.krayseer.domain.ChannelDTO;

import java.util.List;

/**
 * Сервис обработки подписчиков
 */
public interface SubscriberService {


    /**
     * Получить подписчиков канала
     *
     * @param channelId идентификатор канала
     */
    List<ChannelDTO> getSubscribers(Long channelId);

    /**
     * Подписаться на канал
     *
     * @param channelId идентификатор канала
     * @param name      имя пользователя
     */
    void subscribe(Long channelId, String name);

}
