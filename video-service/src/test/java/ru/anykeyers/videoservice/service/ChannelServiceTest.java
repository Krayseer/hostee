package ru.anykeyers.videoservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.anykeyers.videoservice.domain.channel.Channel;
import ru.anykeyers.videoservice.domain.user.User;
import ru.anykeyers.videoservice.domain.channel.CreateChannelDTO;
import ru.anykeyers.videoservice.repository.ChannelRepository;
import ru.anykeyers.videoservice.repository.UserRepository;
import ru.anykeyers.videoservice.service.impl.ChannelServiceImpl;

import java.security.Principal;

import static org.mockito.Mockito.*;

/**
 * Тесты для сервиса {@link ChannelService}
 */
@ExtendWith(MockitoExtension.class)
public class ChannelServiceTest {

    @Mock
    private ChannelRepository channelRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ChannelServiceImpl channelService;

    private final User testUser = User.builder().username(USERNAME).build();

    private static final String USERNAME = "testUser";

    private static final Long CHANNEL_ID = 1L;

    /**
     * Тест на успешное получение канала которого не существует
     */
    @Test
    public void getChannelDoesntExistTest() {
        Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(testUser);
        Mockito.when(channelRepository.findChannelByUser(testUser)).thenReturn(null);

        RuntimeException runtimeException = Assertions.assertThrows(
                RuntimeException.class, () -> channelService.getChannel(USERNAME)
        );
    }

    /**
     * Тест на регистрацию канала который уже существует
     */
    @Test
    public void registerChannelAlreadyExistTest() {
        CreateChannelDTO createChannelDTO = new CreateChannelDTO();
        createChannelDTO.setName("testChannel");

        when(channelRepository.findChannelByName(anyString())).thenReturn(new Channel());

        RuntimeException runtimeException = Assertions.assertThrows(
                RuntimeException.class, () -> channelService.registerChannel(createChannelDTO, mock(Principal.class))
        );
    }

    /**
     * Тест на успешное удаление канала
     */
    @Test
    public void deleteChannelSuccessTest() {
        Channel testChannel = new Channel();
        testChannel.setId(CHANNEL_ID);

        when(channelRepository.findChannelById(CHANNEL_ID)).thenReturn(testChannel);

        Channel resultChannel = channelService.deleteChannel(CHANNEL_ID);

        Assertions.assertNotNull(resultChannel);
        verify(channelRepository, times(1)).delete(testChannel);
    }

    /**
     * Тест на удаление канала которого не существует
     */
    @Test
    public void deleteChannelDoesntExistTest() {
        when(channelRepository.findChannelById(CHANNEL_ID)).thenReturn(null);

        RuntimeException runtimeException = Assertions.assertThrows(
                RuntimeException.class, () -> channelService.deleteChannel(CHANNEL_ID)
        );
    }

    /**
     * Тест на изменение канала
     */
    @Test
    public void updateChannelTest() {
        Channel testChannel = new Channel();
        testChannel.setId(CHANNEL_ID);

        when(channelRepository.save(testChannel)).thenReturn(testChannel);

        Channel resultChannel = channelService.updateChannel(testChannel);

        Assertions.assertNotNull(resultChannel);
        verify(channelRepository, times(1)).save(testChannel);
    }
}
