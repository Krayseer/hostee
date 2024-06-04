package ru.anykeyers.videoservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.videoservice.domain.channel.ChannelRequest;
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

    private final ChannelService channelService;

    @GetMapping
    public ChannelDTO getChannel(Principal user) {
        return channelService.getChannel(user.getName());
    }

    @GetMapping("/{channelId}")
    public ChannelDTO getChannel(@PathVariable Long channelId) {
        return channelService.getChannel(channelId);
    }

    @PostMapping
    public ChannelDTO registerChannel(@RequestBody ChannelRequest channelRequest, Principal user) {
        return channelService.registerChannel(user.getName(), channelRequest);
    }

    @PostMapping("/photo")
    public void addPhoto(@RequestParam("photo") MultipartFile file, Principal principal) {
        channelService.addPhoto(principal.getName(), file);
    }

    @PutMapping("/{id}")
    public ChannelDTO updateChannel(@PathVariable Long id, @RequestBody ChannelRequest channelRequest) {
        return channelService.updateChannel(id, channelRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteChannel(@PathVariable("id") Long id) {
        channelService.deleteChannel(id);
    }

}
