package ru.anykeyers.videoservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.videoservice.domain.channel.Channel;
import ru.anykeyers.videoservice.domain.subscriber.Subscriber;
import ru.anykeyers.videoservice.domain.subscriber.SubscriberMapper;
import ru.anykeyers.videoservice.exception.ChannelNotExistsException;
import ru.anykeyers.videoservice.service.EventService;
import ru.krayseer.domain.ChannelDTO;
import ru.anykeyers.videoservice.domain.channel.ChannelMapper;
import ru.anykeyers.videoservice.repository.ChannelRepository;
import ru.anykeyers.videoservice.repository.SubscriberRepository;
import ru.anykeyers.videoservice.service.SubscriberService;

import java.util.List;

/**
 * Реализация сервиса обработки подписчиков
 */
@Service
@RequiredArgsConstructor
public class SubscriberServiceImpl implements SubscriberService {

    private final EventService eventService;

    private final ChannelRepository channelRepository;

    private final SubscriberRepository subscriberRepository;

    @Override
    public List<ChannelDTO> getSubscribers(Long channelId) {
        List<Subscriber> subscribers = subscriberRepository.findByChannelId(channelId);
        return subscribers.stream()
                .map(Subscriber::getChannel)
                .map(ChannelMapper::createDTO)
                .toList();
    }

    @Override
    public void subscribe(Long channelId, String username) {
        Channel channel = channelRepository.findChannelById(channelId).orElseThrow(
                () -> new ChannelNotExistsException(username)
        );
        Channel subscriberChannel = channelRepository.findChannelByUserUsername(username).orElseThrow(
                () -> new ChannelNotExistsException(username)
        );
        Subscriber subscriber = Subscriber.builder()
                .channel(channel)
                .subscriberChannel(subscriberChannel)
                .build();
        subscriberRepository.save(subscriber);
        eventService.notifySubscribeChannel(SubscriberMapper.createDTO(subscriber));
    }

}
