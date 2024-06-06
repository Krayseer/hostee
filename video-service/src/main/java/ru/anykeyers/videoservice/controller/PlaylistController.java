package ru.anykeyers.videoservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.videoservice.domain.playlist.PlaylistRequest;
import ru.anykeyers.videoservice.domain.playlist.PlaylistUpdateRequest;
import ru.anykeyers.videoservice.domain.playlist.PlaylistDTO;
import ru.anykeyers.videoservice.service.PlaylistService;

import java.security.Principal;
import java.util.List;

@Tag(name = "Обработка плейлистов")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;

    @Operation(summary = "Получить список всех плейлистов")
    @GetMapping
    public List<PlaylistDTO> getAllPlaylists(Principal principal){
        return playlistService.getPlaylists(principal.getName());
    }

    @Operation(summary = "Создать плейлист")
    @PostMapping
    public void createPlaylist(
            @Parameter(description = "Данные о плейлисте") @RequestBody PlaylistRequest playlist,
            Principal principal
    ) {
        playlistService.createPlaylist(principal.getName(), playlist);
    }

    @Operation(summary = "Получить данные о плейлисте")
    @GetMapping("/{id}")
    public PlaylistDTO getPlaylistVideo(
            @Parameter(description = "Идентификатор плейлиста") @PathVariable("id") Long id
    ) {
        return playlistService.getPlaylist(id);
    }

    @Operation(summary = "Добавить видео в плейлист")
    @PostMapping("/video")
    public void addPlaylistVideo(
            @Parameter(description = "Данные для добавления видео") @RequestBody PlaylistUpdateRequest playlistUpdateRequest
    ) {
        playlistService.addVideoInPlaylist(playlistUpdateRequest.getPlaylistId(), playlistUpdateRequest.getVideoId());
    }

}
