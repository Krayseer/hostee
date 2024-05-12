package ru.anykeyers.notificationservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.anykeyers.notificationservice.service.SubscriberService;
import ru.krayseer.MessageQueue;
import ru.krayseer.domain.dto.SubscriberDTO;

/**
 * Обработчик сообщений Kafka
 */
@Component
@RequiredArgsConstructor
public class KafkaProcessor {

    private static final String GROUP_ID = "notification-group";

    private final ObjectMapper objectMapper;

    private final SubscriberService subscriberService;

    /**
     * Слушатель подписки на каналы
     */
    @SneakyThrows
    @KafkaListener(topics = MessageQueue.SUBSCRIBE_CHANNEL, groupId = GROUP_ID)
    public void receiveSubscribeChannel(String subscriberMessage) {
        SubscriberDTO subscriber = objectMapper.readValue(subscriberMessage, new TypeReference<>() {});
        subscriberService.notifySubscribeChannel(subscriber);
    }

}
