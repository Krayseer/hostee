package ru.anykeyers.videoservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.videoservice.domain.playlist.PlaylistRequest;
import ru.anykeyers.videoservice.domain.playlist.PlaylistVideoRequest;
import ru.anykeyers.videoservice.domain.playlist.PlaylistVideoResponse;
import ru.anykeyers.videoservice.service.PlaylistService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping("{id}")
    public PlaylistVideoResponse getPlaylistVideo(@PathVariable("id") Long id) {
        return playlistService.getVideos(id);
    }

    @PostMapping
    public void createPlaylist(@RequestBody PlaylistRequest playlist, Principal principal) {
        playlistService.createPlaylist(principal.getName(), playlist);
    }

    @PostMapping("/video")
    public void addPlaylistVideo(@RequestBody PlaylistVideoRequest playlistVideoRequest) {
        playlistService.addVideoInPlaylist(playlistVideoRequest.getPlaylistId(), playlistVideoRequest.getVideoId());
    }

}
