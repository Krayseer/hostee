package ru.anykeyers.videoservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.videoservice.domain.playlist.PlaylistRequest;
import ru.anykeyers.videoservice.domain.playlist.PlaylistUpdateRequest;
import ru.anykeyers.videoservice.domain.playlist.PlaylistDTO;
import ru.anykeyers.videoservice.service.PlaylistService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping("{id}")
    public PlaylistDTO getPlaylistVideo(@PathVariable("id") Long id) {
        return playlistService.getVideos(id);
    }

    @PostMapping
    public void createPlaylist(@RequestBody PlaylistRequest playlist, Principal principal) {
        playlistService.createPlaylist(principal.getName(), playlist);
    }

    @PostMapping("/video")
    public void addPlaylistVideo(@RequestBody PlaylistUpdateRequest playlistUpdateRequest) {
        playlistService.addVideoInPlaylist(playlistUpdateRequest.getPlaylistId(), playlistUpdateRequest.getVideoId());
    }

}
