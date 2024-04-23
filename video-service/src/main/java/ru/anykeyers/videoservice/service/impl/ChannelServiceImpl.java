package ru.anykeyers.videoservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.anykeyers.videoservice.domain.Channel;
import ru.anykeyers.videoservice.domain.User;
import ru.anykeyers.videoservice.domain.dto.CreateChannelDTO;
import ru.anykeyers.videoservice.factory.ChannelFactory;
import ru.anykeyers.videoservice.repository.ChannelRepository;
import ru.anykeyers.videoservice.repository.UserRepository;
import ru.anykeyers.videoservice.service.ChannelService;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;

    private final UserRepository userRepository;

    private final ChannelFactory channelFactory;

    @Override
    public Channel getChannel(String username) {
        User user = userRepository.findByUsername(username);
        Channel channel = channelRepository.findChannelByUser(user);
        if (channel == null) {
            throw new RuntimeException("Channel doesn't exist");
        }

        return channel;
    }

    @Override
    public Channel registerChannel(CreateChannelDTO createChannelDTO, Principal user) {
        User currentUser= userRepository.findByUsername(user.getName());
        if (channelRepository.findChannelByName(createChannelDTO.getName()) != null) {
            throw new RuntimeException("Channel already exist");
        }
        Channel channelFromDto = channelFactory.createChannelFromDTO(createChannelDTO, currentUser);
        channelRepository.save(channelFromDto);
        log.info("Successful registration of channel with name: {}", createChannelDTO.getName());
        return channelFromDto;
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

    @Override
    public Channel updateChannel(Channel channel) {
        channelRepository.save(channel);
        log.info("Channel with id: {}, updated", channel.getId());
        return channel;
    }
}
