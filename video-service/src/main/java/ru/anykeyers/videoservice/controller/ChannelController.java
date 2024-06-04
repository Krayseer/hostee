package ru.anykeyers.videoservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.videoservice.domain.channel.Channel;
import ru.anykeyers.videoservice.domain.channel.CreateChannelDTO;
import ru.anykeyers.videoservice.domain.channel.ChannelMapper;
import ru.anykeyers.videoservice.service.ChannelService;
import ru.krayseer.domain.ChannelDTO;

import java.security.Principal;

/**
 * REST контроллер для работы с каналами
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/channel")
public class ChannelController {

    private final ChannelMapper channelMapper;

    private final ChannelService channelService;

    @GetMapping
    public Channel getChannel(Principal user) {
        return channelService.getChannel(user.getName());
    }

    @GetMapping("/{channelId}")
    public ChannelDTO getChannel(@PathVariable Long channelId) {
        return channelService.getChannel(channelId);
    }

    @PostMapping
    public Channel registerChannel(@RequestBody CreateChannelDTO createChannelDTO, Principal user) {
        return channelService.registerChannel(createChannelDTO, user);
    }

    @PostMapping("/photo")
    public void addPhoto(@RequestParam("photo") MultipartFile file, Principal principal) {
        channelService.addPhoto(principal.getName(), file);
    }

    @PutMapping
    public Channel updateChannel(@RequestBody Channel channel) {
        return channelService.updateChannel(channel);
    }

    @DeleteMapping("/{id}")
    public Channel deleteChannel(@PathVariable("id") Long id) {
        return channelService.deleteChannel(id);
    }

}
