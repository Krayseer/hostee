package ru.anykeyers.videoservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.videoservice.domain.Channel;
import ru.anykeyers.videoservice.domain.dto.CreateChannelDTO;
import ru.anykeyers.videoservice.service.ChannelService;

import java.security.Principal;

/**
 * REST контроллер для работы с каналами
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/channel")
public class ChannelController {

    private final ChannelService channelService;

    @GetMapping
    public Channel getChannel(Principal user) {
        return channelService.getChannel(user.getName());
    }

    @PostMapping
    public Channel registerChannel(@RequestBody @Valid CreateChannelDTO createChannelDTO, Principal user) {
        return channelService.registerChannel(createChannelDTO, user);
    }

    @PutMapping
    public Channel updateChannel(@RequestBody Channel channel) {
        return channelService.updateChannel(channel);
    }

    @DeleteMapping
    public Channel deleteChannel(@PathVariable("id") Long id) {
        return channelService.deleteChannel(id);
    }
}
