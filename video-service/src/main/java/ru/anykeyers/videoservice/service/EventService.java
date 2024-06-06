package ru.anykeyers.videoservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.krayseer.MessageQueue;
import ru.krayseer.domain.SubscriberDTO;

/**
 * Сервис обработки уведомлений
 */
@Service
@RequiredArgsConstructor
public class EventService {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Уведомление о просмотре видео
     *
     * @param id идентификатор видео
     */
    public void notifyWatchVideo(Long id) {
        kafkaTemplate.send(MessageQueue.WATCHING_VIDEO, String.valueOf(id));
    }

    /**
     * Уведомить о новом подписчике
     *
     * @param subscriberDTO данные о подписчике
     */
    @SneakyThrows
    public void notifySubscribeChannel(SubscriberDTO subscriberDTO) {
        kafkaTemplate.send(MessageQueue.SUBSCRIBE_CHANNEL, objectMapper.writeValueAsString(subscriberDTO));
    }

    /**
     * Уведомить о просмотре канала
     *
     * @param channelId идентификатор канала
     */
    public void notifyWatchChannel(String channelId) {
        kafkaTemplate.send(MessageQueue.WATCHING_CHANNEL, channelId);
    }

}
