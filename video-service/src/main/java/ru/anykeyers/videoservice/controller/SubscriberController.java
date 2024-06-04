package ru.anykeyers.videoservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.krayseer.domain.ChannelDTO;
import ru.anykeyers.videoservice.service.SubscriberService;

import java.security.Principal;
import java.util.List;

/**
 * REST контроллер для обработки запросов уведомлений
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/subscribe")
public class SubscriberController {

    private final SubscriberService subscriberService;

    @GetMapping("/{channelId}")
    public List<ChannelDTO> getChannelSubscribers(@PathVariable Long channelId) {
        return subscriberService.getSubscribers(channelId);
    }

    @PostMapping
    public void subscribe(Principal principal, @RequestBody Long channelId) {
        subscriberService.subscribe(channelId, principal.getName());
    }

}
