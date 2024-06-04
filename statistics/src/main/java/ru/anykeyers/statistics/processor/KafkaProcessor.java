package ru.anykeyers.statistics.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.anykeyers.statistics.service.ChannelStatisticsService;
import ru.anykeyers.statistics.service.VideoStatisticsService;
import ru.krayseer.MessageQueue;
import ru.krayseer.domain.SubscriberDTO;

/**
 * Обработчик сообщений, поступающих по Kafka
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProcessor {

    private static final String GROUP_ID = "statistics-group";

    private final ObjectMapper objectMapper;

    private final VideoStatisticsService videoStatisticsService;

    private final ChannelStatisticsService channelStatisticsService;

    @KafkaListener(topics = MessageQueue.WATCHING_VIDEO, groupId = GROUP_ID)
    public void receiveWatchingVideo(String videoUuid) {
        videoStatisticsService.handleWatchVideo(videoUuid);
    }

    @KafkaListener(topics = MessageQueue.WATCHING_CHANNEL, groupId = GROUP_ID)
    public void receiveWatchingChannel(Long channelUuid) {
        channelStatisticsService.handleWatchChannel(channelUuid);
    }

    @SneakyThrows
    @KafkaListener(topics = MessageQueue.SUBSCRIBE_CHANNEL, groupId = GROUP_ID)
    public void receiveSubscribeChannel(String subscriberDTO) {
        SubscriberDTO subscriber = objectMapper.readValue(subscriberDTO, SubscriberDTO.class);
        channelStatisticsService.handleSubscribeChannel(subscriber.getChannel().getId());
    }

}
