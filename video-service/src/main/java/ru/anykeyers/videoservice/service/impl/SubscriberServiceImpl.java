package ru.anykeyers.videoservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.anykeyers.videoservice.domain.Channel;
import ru.anykeyers.videoservice.domain.Subscriber;
import ru.anykeyers.videoservice.factory.SubscriberFactory;
import ru.krayseer.domain.dto.ChannelDTO;
import ru.anykeyers.videoservice.factory.ChannelFactory;
import ru.anykeyers.videoservice.repository.ChannelRepository;
import ru.anykeyers.videoservice.repository.SubscriberRepository;
import ru.anykeyers.videoservice.service.SubscriberService;
import ru.krayseer.MessageQueue;

import java.util.List;

/**
 * Реализация сервиса обработки подписчиков
 */
@Service
@RequiredArgsConstructor
public class SubscriberServiceImpl implements SubscriberService {

    private final ObjectMapper objectMapper;

    private final ChannelFactory channelFactory;

    private final SubscriberFactory subscriberFactory;

    private final ChannelRepository channelRepository;

    private final SubscriberRepository subscriberRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public List<ChannelDTO> getSubscribers(Long channelId) {
        List<Subscriber> subscribers = subscriberRepository.findByChannelId(channelId);
        return subscribers.stream()
                .map(Subscriber::getChannel)
                .map(channelFactory::createDTO)
                .toList();
    }

    @Override
    @SneakyThrows
    public void subscribe(Long channelId, String username) {
        Channel channel = channelRepository.findChannelById(channelId);
        Subscriber subscriber = new Subscriber();
        subscriber.setChannel(channel);
        subscriber.setSubscriberChannel(channelRepository.findChannelByUserUsername(username));
        subscriberRepository.save(subscriber);
        kafkaTemplate.send(
                MessageQueue.SUBSCRIBE_CHANNEL, objectMapper.writeValueAsString(subscriberFactory.createDTO(subscriber))
        );
    }

}
