package ru.anykeyers.videoservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.anykeyers.videoservice.domain.channel.Channel;
import ru.anykeyers.videoservice.domain.subscriber.Subscriber;
import ru.anykeyers.videoservice.domain.user.User;
import ru.anykeyers.videoservice.domain.user.UserSetting;
import ru.anykeyers.videoservice.exception.ChannelNotExistsException;
import ru.anykeyers.videoservice.repository.ChannelRepository;
import ru.anykeyers.videoservice.repository.SubscriberRepository;
import ru.anykeyers.videoservice.service.impl.SubscriberServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для {@link SubscriberService}
 */
@ExtendWith(MockitoExtension.class)
class SubscriberServiceTest {

    @Mock
    private EventService eventService;

    @Mock
    private ChannelRepository channelRepository;

    @Mock
    private SubscriberRepository subscriberRepository;

    @InjectMocks
    private SubscriberServiceImpl subscriberService;

    @Captor
    private ArgumentCaptor<Subscriber> subscriberCaptor;

    /**
     * Тест подписки на несуществующий канал
     */
    @Test
    void subscribeToNotExistsChannel() {
        Long channelId = 1L;

        Mockito.when(channelRepository.findChannelById(channelId)).thenReturn(Optional.empty());

        ChannelNotExistsException exception = Assertions.assertThrows(
                ChannelNotExistsException.class, () -> subscriberService.subscribe(channelId, "user")
        );

        Assertions.assertEquals("Channel not exists with id: 1", exception.getMessage());
    }

    /**
     * Тест подписки с несуществующего канала
     */
    @Test
    void subscribeFromNotExistsChannel() {
        Mockito.when(channelRepository.findChannelById(1L)).thenReturn(Optional.of(Mockito.mock(Channel.class)));
        Mockito.when(channelRepository.findChannelByUserUsername("non-existing-user")).thenReturn(Optional.empty());

        ChannelNotExistsException exception = Assertions.assertThrows(
                ChannelNotExistsException.class, () -> subscriberService.subscribe(1L, "non-existing-user")
        );

        Assertions.assertEquals("Channel not exists for user: non-existing-user", exception.getMessage());
    }

    /**
     * Тест успешной подписки на канал
     */
    @Test
    void subscribe() {
        User user = User.builder().username("user").userSetting(new UserSetting()).build();
        User subscriberUser =  User.builder().username("subscriber").userSetting(new UserSetting()).build();
        Channel subscriberChannel = Channel.builder().user(subscriberUser).id(1L).build();
        Channel channel = Channel.builder().id(2L).user(user).build();

        Mockito.when(channelRepository.findChannelById(2L)).thenReturn(Optional.of(channel));
        Mockito.when(channelRepository.findChannelByUserUsername("user")).thenReturn(Optional.of(subscriberChannel));

        subscriberService.subscribe(2L, "user");

        Mockito.verify(subscriberRepository, Mockito.times(1)).save(subscriberCaptor.capture());
        Subscriber subscriber = subscriberCaptor.getValue();
        Assertions.assertEquals(channel, subscriber.getChannel());
        Assertions.assertEquals(subscriberChannel, subscriber.getSubscriberChannel());
        Mockito.verify(eventService, Mockito.times(1)).notifySubscribeChannel(Mockito.any());
    }

}