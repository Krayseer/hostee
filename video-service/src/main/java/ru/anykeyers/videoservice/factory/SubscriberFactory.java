package ru.anykeyers.videoservice.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.anykeyers.videoservice.domain.Subscriber;
import ru.krayseer.domain.dto.SubscriberDTO;

/**
 * Фабрика по созданию DTO о подписчиках
 */
@Component
@RequiredArgsConstructor
public class SubscriberFactory {

    private final ChannelFactory channelFactory;

    /**
     * Создать DTO с данными о подписчике
     *
     * @param subscriber подписчик
     */
    public SubscriberDTO createDTO(Subscriber subscriber) {
        return new SubscriberDTO(
                channelFactory.createDTO(subscriber.getChannel()),
                channelFactory.createDTO(subscriber.getSubscriberChannel())
        );
    }

}
