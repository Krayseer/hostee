package ru.krayseer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO с данными о подписчике
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriberDTO {

    /**
     * Канал, на который подписались
     */
    private ChannelDTO channel;

    /**
     * Канал подписчик
     */
    private ChannelDTO subscriberChannel;

}
