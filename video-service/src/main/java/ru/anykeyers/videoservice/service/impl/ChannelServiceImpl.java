package ru.anykeyers.videoservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.videoservice.domain.channel.Channel;
import ru.anykeyers.videoservice.domain.user.User;
import ru.anykeyers.videoservice.domain.channel.ChannelRequest;
import ru.anykeyers.videoservice.domain.channel.ChannelMapper;
import ru.anykeyers.videoservice.exception.ChannelAlreadyExistsException;
import ru.anykeyers.videoservice.exception.ChannelNotExistsException;
import ru.anykeyers.videoservice.repository.ChannelRepository;
import ru.anykeyers.videoservice.repository.UserRepository;
import ru.anykeyers.videoservice.service.ChannelService;
import ru.anykeyers.videoservice.service.EventService;
import ru.krayseer.service.RemoteStorageService;
import ru.krayseer.domain.ChannelDTO;

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
    public ChannelDTO getChannel(String username) {
        User user = userRepository.findByUsername(username);
        Channel channel = channelRepository.findChannelByUser(user).orElseThrow(
                () -> new ChannelNotExistsException(username)
        );
        eventService.notifyWatchChannel(String.valueOf(channel.getId()));
        return ChannelMapper.createDTO(channel);
    }

    @Override
    public ChannelDTO getChannel(Long id) {
        Channel channel = channelRepository.findChannelById(id).orElseThrow(
                () -> new ChannelNotExistsException(id)
        );
        return ChannelMapper.createDTO(channel);
    }

    @Override
    public ChannelDTO registerChannel(String username, ChannelRequest channelRequest) {
        User user = userRepository.findByUsername(username);
        if (channelRepository.existsChannelByName(channelRequest.getName())) {
            throw new ChannelAlreadyExistsException(username);
        }
        Channel channel = ChannelMapper.createChannel(channelRequest, user);
        channelRepository.save(channel);
        log.info("Successful registration of channel with name: {}", channelRequest.getName());
        return ChannelMapper.createDTO(channel);
    }

    @Override
    public void addPhoto(String username, MultipartFile photo) {
        Channel channel = channelRepository.findChannelByUserUsername(username).orElseThrow(
                () -> new ChannelNotExistsException(username)
        );
        ResponseEntity<String> photoResponse = remoteStorageService.uploadPhoto(photo);
        if (photoResponse.getBody() == null) {
            throw new RuntimeException("Error upload photo");
        }
        channel.setPhotoUrl(photoResponse.getBody());
        channelRepository.save(channel);
        log.info("Successful upload photo for channel: {}", channel.getName());
    }

    @Override
    public ChannelDTO updateChannel(Long id, ChannelRequest channelRequest) {
        Channel channel = channelRepository.findChannelById(id).orElseThrow(
                () -> new ChannelNotExistsException(id)
        );
        channel.setName(channelRequest.getName());
        channel.setDescription(channelRequest.getDescription());
        channelRepository.save(channel);
        log.info("Channel with id: {}, updated", channel.getId());
        return ChannelMapper.createDTO(channel);
    }

    @Override
    public void deleteChannel(Long id) {
        channelRepository.deleteById(id);
        log.info("Deleted channel with id: {}", id);
    }

}
