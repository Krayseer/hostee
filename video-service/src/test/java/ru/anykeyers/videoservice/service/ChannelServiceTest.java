package ru.anykeyers.videoservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.videoservice.domain.channel.Channel;
import ru.anykeyers.videoservice.domain.channel.ChannelRequest;
import ru.anykeyers.videoservice.domain.user.User;
import ru.anykeyers.videoservice.exception.ChannelAlreadyExistsException;
import ru.anykeyers.videoservice.exception.ChannelNotExistsException;
import ru.anykeyers.videoservice.repository.ChannelRepository;
import ru.anykeyers.videoservice.repository.UserRepository;
import ru.anykeyers.videoservice.service.impl.ChannelServiceImpl;
import ru.krayseer.domain.ChannelDTO;
import ru.krayseer.service.RemoteStorageService;

import java.util.Optional;

/**
 * Тесты для {@link ChannelService}
 */
@ExtendWith(MockitoExtension.class)
class ChannelServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ChannelRepository channelRepository;

    @Mock
    private RemoteStorageService remoteStorageService;

    @InjectMocks
    private ChannelServiceImpl channelService;

    private final User user = User.builder().username("test-user").build();

    /**
     * Тест регистрации канала для пользователя, у которого уже существует канал
     */
    @Test
    void registerChannelWithUserWhenAlreadyExistsChannel() {
        ChannelRequest channelRequest = new ChannelRequest("test-channel", "description");

        Mockito.when(userRepository.findByUsername("test-user")).thenReturn(user);
        Mockito.when(channelRepository.existsChannelByName("test-channel")).thenReturn(true);

        ChannelAlreadyExistsException exception = Assertions.assertThrows(
                ChannelAlreadyExistsException.class, () -> channelService.registerChannel("test-user", channelRequest)
        );

        Assertions.assertEquals("Channel already exists for user: test-user", exception.getMessage());
    }

    /**
     * Тест успешного создания канала пользователю
     */
    @Test
    void registerChannel() {
        ChannelRequest channelRequest = new ChannelRequest("test-channel", "description");

        Mockito.when(userRepository.findByUsername("test-user")).thenReturn(user);

        ChannelDTO channelDTO = channelService.registerChannel("test-user", channelRequest);

        Mockito.verify(channelRepository, Mockito.times(1)).save(Mockito.any());
        Assertions.assertEquals("test-channel", channelDTO.getName());
        Assertions.assertEquals("description", channelDTO.getDescription());
    }

    /**
     * Тест добавления фотографии несуществующему каналу
     */
    @Test
    void addPhotoForNotExistingChannel() {
        Mockito.when(channelRepository.findChannelByUserUsername("test-user")).thenReturn(Optional.empty());

        ChannelNotExistsException exception = Assertions.assertThrows(
                ChannelNotExistsException.class, () -> channelService.addPhoto("test-user", Mockito.mock(MultipartFile.class))
        );

        Assertions.assertEquals("Channel not exists for user: test-user", exception.getMessage());
    }

    /**
     * Тест неуспешного добавления фотографии из-за некорректного ответа с удаленного сервиса хранилища
     */
    @Test
    void addPhotoWithIncorrectResponseFromRemoteStorageService() {
        MultipartFile file = Mockito.mock(MultipartFile.class);

        Mockito.when(channelRepository.findChannelByUserUsername("test-user"))
                .thenReturn(Optional.ofNullable(Mockito.mock(Channel.class)));
        Mockito.when(remoteStorageService.uploadPhoto(file))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.BAD_GATEWAY));

        RuntimeException exception = Assertions.assertThrows(
                RuntimeException.class, () -> channelService.addPhoto("test-user", file)
        );

        Assertions.assertEquals("Error upload photo", exception.getMessage());
    }

    /**
     * Тест успешного добавления фотографии каналу
     */
    @Test
    void addPhoto() {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        Channel channel = new Channel();

        Mockito.when(channelRepository.findChannelByUserUsername("test-user")).thenReturn(Optional.of(channel));
        Mockito.when(remoteStorageService.uploadPhoto(file))
                .thenReturn(new ResponseEntity<>("uuid", HttpStatus.BAD_GATEWAY));

        channelService.addPhoto("test-user", file);

        Mockito.verify(channelRepository, Mockito.times(1)).save(Mockito.any());
        Assertions.assertEquals("uuid", channel.getPhotoUrl());
    }

    /**
     * Тест обновления несуществующего канала
     */
    @Test
    void updateNotExistingChannel() {
        Mockito.when(channelRepository.findChannelById(1L)).thenReturn(Optional.empty());

        ChannelNotExistsException exception = Assertions.assertThrows(
                ChannelNotExistsException.class, () -> channelService.updateChannel(1L, Mockito.mock(ChannelRequest.class))
        );

        Assertions.assertEquals("Channel not exists with id: 1", exception.getMessage());
    }

    /**
     * Тест успешного обновления данных о канале
     */
    @Test
    void updateChannel() {
        Channel channel = Channel.builder().user(user).name("first-name").description("description").build();
        ChannelRequest channelRequest = new ChannelRequest("second-name", "description");

        Mockito.when(channelRepository.findChannelById(1L)).thenReturn(Optional.of(channel));

        ChannelDTO channelDTO = channelService.updateChannel(1L, channelRequest);

        Mockito.verify(channelRepository, Mockito.times(1)).save(Mockito.any());
        Assertions.assertEquals("second-name", channelDTO.getName());
        Assertions.assertEquals("description", channelDTO.getDescription());
    }

    /**
     * Тест удаления канала
     */
    @Test
    void deleteChannel() {
        channelService.deleteChannel(1L);
        Mockito.verify(channelRepository, Mockito.times(1)).deleteById(1L);
    }

}