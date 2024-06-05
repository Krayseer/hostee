package ru.anykeyers.videoservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.krayseer.domain.ChannelDTO;
import ru.anykeyers.videoservice.service.SubscriberService;

import java.security.Principal;
import java.util.List;

@Tag(name = "Обработка подписчиков")
@RestController
@RequiredArgsConstructor
@RequestMapping("/subscribe")
public class SubscriberController {

    private final SubscriberService subscriberService;

    @Operation(summary = "Получить подписчиков канала")
    @GetMapping("/{channelId}")
    public List<ChannelDTO> getChannelSubscribers(
            @Parameter(description = "Идентификатор канала") @PathVariable Long channelId
    ) {
        return subscriberService.getSubscribers(channelId);
    }

    @Operation(summary = "Подписаться на канал")
    @PostMapping
    public void subscribe(
            @Parameter(description = "Идентификатор канала") @RequestBody Long channelId,
            Principal principal
    ) {
        subscriberService.subscribe(channelId, principal.getName());
    }

}
