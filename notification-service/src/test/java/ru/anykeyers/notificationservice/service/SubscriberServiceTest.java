package ru.anykeyers.notificationservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.anykeyers.notificationservice.service.impl.EmailNotificationService;
import ru.anykeyers.notificationservice.service.impl.PushNotificationService;
import ru.krayseer.domain.dto.ChannelDTO;
import ru.krayseer.domain.dto.NotificationSettingDTO;
import ru.krayseer.domain.dto.SubscriberDTO;
import ru.krayseer.domain.dto.UserDTO;

import java.util.Collections;
import java.util.List;

/**
 * Тесты для класса {@link SubscriberService}
 */
@ExtendWith(MockitoExtension.class)
class SubscriberServiceTest {

    @Mock
    private PushNotificationService pushNotificationService;

    @Mock
    private EmailNotificationService emailNotificationService;

    @Mock
    private ServiceCompound serviceCompound;

    @InjectMocks
    private SubscriberService subscriberService;

    private UserDTO userDTO;

    private ChannelDTO channelDTO;

    private ChannelDTO subscriberChannelDTO;

    @BeforeEach
    public void setUp() {
        NotificationSettingDTO notificationSettingDTO = new NotificationSettingDTO(true, true);
        userDTO = new UserDTO();
        userDTO.setId(0L);
        userDTO.setEmail("test@email.com");
        userDTO.setNotificationSettingUser(notificationSettingDTO);
        userDTO.setUsername("testUser");
        channelDTO = new ChannelDTO();
        channelDTO.setId(0L);
        channelDTO.setName("testChannel");
        channelDTO.setDescription("Test channel");
        channelDTO.setUser(userDTO);
        UserDTO subscriberUserDTO = new UserDTO();
        subscriberUserDTO.setId(1L);
        subscriberUserDTO.setEmail("subscriber@email.com");
        subscriberUserDTO.setUsername("subscriberUser");
        subscriberChannelDTO = new ChannelDTO();
        subscriberChannelDTO.setId(0L);
        subscriberChannelDTO.setName("subscriberChannel");
        subscriberChannelDTO.setDescription("Test subscriber channel");
        subscriberChannelDTO.setUser(subscriberUserDTO);
    }

    /**
     * Тестирование отправки уведомлений при выключенных настройках уведомлений по push и email
     */
    @Test
    void doesntNotifySubscribe() {
        SubscriberDTO subscriberDTO = new SubscriberDTO(channelDTO, subscriberChannelDTO);
        Mockito
                .when(serviceCompound.getNotificationServices(userDTO.getNotificationSettingUser()))
                .thenReturn(Collections.emptyList());
        subscriberService.notifySubscribeChannel(subscriberDTO);
        Mockito.verify(pushNotificationService, Mockito.times(0)).notify(Mockito.any(), Mockito.any());
        Mockito.verify(emailNotificationService, Mockito.times(0)).notify(Mockito.any(), Mockito.any());
    }

    /**
     * Тестирование отправки уведомлений при включенной настройке только push уведомлений
     */
    @Test
    void notifySubscribeChannelPush() {
        SubscriberDTO subscriberDTO = new SubscriberDTO(channelDTO, subscriberChannelDTO);
        Mockito
                .when(serviceCompound.getNotificationServices(userDTO.getNotificationSettingUser()))
                .thenReturn(List.of(pushNotificationService));
        subscriberService.notifySubscribeChannel(subscriberDTO);
        String expectedContent = """
                У вас появился новый подписчик!
                Канал: subscriberChannel
                """;
        Mockito.verify(pushNotificationService, Mockito.times(1)).notify(userDTO, expectedContent);
        Mockito.verify(emailNotificationService, Mockito.times(0)).notify(Mockito.any(), Mockito.any());
    }

    /**
     * Тестирование отправки уведомлений при включенных настройках push и email уведомлений
     */
    @Test
    void notifySubscribeChannelPushAndEmail() {
        SubscriberDTO subscriberDTO = new SubscriberDTO(channelDTO, subscriberChannelDTO);
        Mockito
                .when(serviceCompound.getNotificationServices(userDTO.getNotificationSettingUser()))
                .thenReturn(List.of(pushNotificationService, emailNotificationService));
        subscriberService.notifySubscribeChannel(subscriberDTO);
        String expectedContent = """
                У вас появился новый подписчик!
                Канал: subscriberChannel
                """;
        Mockito.verify(pushNotificationService, Mockito.times(1)).notify(userDTO, expectedContent);
        Mockito.verify(emailNotificationService, Mockito.times(1)).notify(userDTO, expectedContent);
    }

}