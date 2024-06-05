package ru.anykeyers.videoservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.videoservice.domain.channel.ChannelRequest;
import ru.anykeyers.videoservice.service.ChannelService;
import ru.krayseer.domain.ChannelDTO;

import java.security.Principal;

@Tag(name = "Обработка каналов")
@RestController
@RequiredArgsConstructor
@RequestMapping("/channel")
public class ChannelController {

    private final ChannelService channelService;

    @Operation(summary = "Получить канал авторизованного пользователя")
    @GetMapping
    public ChannelDTO getChannel(Principal user) {
        return channelService.getChannel(user.getName());
    }

    @Operation(summary = "Получить канал")
    @GetMapping("/{channelId}")
    public ChannelDTO getChannel(
            @Parameter(description = "Идентификатор канала") @PathVariable Long channelId
    ) {
        return channelService.getChannel(channelId);
    }

    @Operation(summary = "Зарегистрировать канал")
    @PostMapping
    public ChannelDTO registerChannel(
            @Parameter(description = "Данные о канале") @RequestBody ChannelRequest channelRequest,
            Principal user
    ) {
        return channelService.registerChannel(user.getName(), channelRequest);
    }

    @Operation(summary = "Добавить фотографию канала")
    @PostMapping("/photo")
    public void addPhoto(
            @Parameter(description = "Файл фотографии") @RequestParam("photo") MultipartFile file,
            Principal principal
    ) {
        channelService.addPhoto(principal.getName(), file);
    }

    @Operation(summary = "Обновить информацию о канале")
    @PutMapping("/{id}")
    public ChannelDTO updateChannel(
            @Parameter(description = "Идентификатор канала") @PathVariable Long id,
            @Parameter(description = "Данные о канале") @RequestBody ChannelRequest channelRequest
    ) {
        return channelService.updateChannel(id, channelRequest);
    }

    @Operation(summary = "Удалить канал")
    @DeleteMapping("/{id}")
    public void deleteChannel(
            @Parameter(description = "Идентификатор канала") @PathVariable("id") Long id
    ) {
        channelService.deleteChannel(id);
    }

}
