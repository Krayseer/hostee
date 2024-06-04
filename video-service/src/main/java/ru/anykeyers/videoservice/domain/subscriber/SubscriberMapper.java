package ru.anykeyers.videoservice.domain.subscriber;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.anykeyers.videoservice.domain.channel.ChannelMapper;
import ru.krayseer.domain.SubscriberDTO;

/**
 * Фабрика по созданию DTO о подписчиках
 */
@Component
@RequiredArgsConstructor
public final class SubscriberMapper {

    /**
     * Создать DTO с данными о подписчике
     *
     * @param subscriber подписчик
     */
    public static SubscriberDTO createDTO(Subscriber subscriber) {
        return new SubscriberDTO(
                ChannelMapper.createDTO(subscriber.getChannel()),
                ChannelMapper.createDTO(subscriber.getSubscriberChannel())
        );
    }

}
