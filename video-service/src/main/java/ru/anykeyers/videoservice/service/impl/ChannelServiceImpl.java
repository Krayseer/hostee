package ru.anykeyers.videoservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.videoservice.domain.channel.Channel;
import ru.anykeyers.videoservice.domain.user.User;
import ru.anykeyers.videoservice.domain.channel.CreateChannelDTO;
import ru.anykeyers.videoservice.domain.channel.ChannelMapper;
import ru.anykeyers.videoservice.repository.ChannelRepository;
import ru.anykeyers.videoservice.repository.UserRepository;
import ru.anykeyers.videoservice.service.ChannelService;
import ru.anykeyers.videoservice.service.EventService;
import ru.krayseer.service.RemoteStorageService;
import ru.krayseer.domain.ChannelDTO;

import java.security.Principal;

/**
 * Реализация сервиса для работы с каналами
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final EventService eventService;

    private final UserRepository userRepository;

    private final ChannelRepository channelRepository;

    private final RemoteStorageService remoteStorageService;

    @Override
    public Channel getChannel(String username) {
        User user = userRepository.findByUsername(username);
        Channel channel = channelRepository.findChannelByUser(user);
        if (channel == null) {
            throw new RuntimeException("Channel doesn't exist");
        }
        eventService.notifyWatchChannel(String.valueOf(channel.getId()));
        return channel;
    }

    @Override
    public ChannelDTO getChannel(Long id) {
        return ChannelMapper.createDTO(channelRepository.findChannelById(id));
    }

    @Override
    public Channel registerChannel(CreateChannelDTO createChannelDTO, Principal user) {
        User currentUser= userRepository.findByUsername(user.getName());
        if (channelRepository.findChannelByName(createChannelDTO.getName()) != null) {
            throw new RuntimeException("Channel already exist");
        }
        Channel channelFromDto = ChannelMapper.createChannel(createChannelDTO, currentUser);
        channelRepository.save(channelFromDto);
        log.info("Successful registration of channel with name: {}", createChannelDTO.getName());
        return channelFromDto;
    }

    @Override
    public void addPhoto(String username, MultipartFile photo) {
        Channel channel = channelRepository.findChannelByUserUsername(username);
        if (channel == null) {
            throw new RuntimeException("Channel doesn't exist");
        }
        ResponseEntity<String> photoResponse = remoteStorageService.uploadPhoto(photo);
        if (photoResponse.getBody() == null) {
            throw new RuntimeException("Error upload photo");
        }
        channel.setPhotoUrl(photoResponse.getBody());
        channelRepository.save(channel);
        log.info("Successful upload photo for channel: {}", channel.getName());
    }

    @Override
    public Channel updateChannel(Channel channel) {
        channelRepository.save(channel);
        log.info("Channel with id: {}, updated", channel.getId());
        return channel;
    }

    @Override
    public Channel deleteChannel(Long id) {
        Channel channel = channelRepository.findChannelById(id);
        if (channel == null) {
            throw new RuntimeException("Channel doesn't exist");
        }
        channelRepository.delete(channel);
        log.info("Deleted channel with id: {}", channel.getId());
        return channel;
    }

}
