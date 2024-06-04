package ru.anykeyers.videoservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.anykeyers.videoservice.domain.channel.Channel;
import ru.anykeyers.videoservice.domain.playlist.Playlist;
import ru.anykeyers.videoservice.domain.video.Video;
import ru.anykeyers.videoservice.domain.playlist.PlaylistRequest;
import ru.anykeyers.videoservice.domain.playlist.PlaylistResponse;
import ru.anykeyers.videoservice.repository.ChannelRepository;
import ru.anykeyers.videoservice.repository.PlaylistRepository;
import ru.anykeyers.videoservice.repository.VideoRepository;
import ru.anykeyers.videoservice.service.PlaylistService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;

    private final VideoRepository videoRepository;

    private final ChannelRepository channelRepository;

    @Override
    public PlaylistResponse getVideos(Long id) {
        List<Video> videos = playlistRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Playlist not found")
        ).getVideos();
        return new PlaylistResponse(videos);

    }

    @Override
    public void createPlaylist(String username, PlaylistRequest playlistRequest) {
        Channel channel = channelRepository.findChannelByUserUsername(username);
        Playlist playlist = Playlist.builder()
                .name(playlistRequest.getName())
                .description(playlistRequest.getDescription())
                .channel(channel)
                .build();
        playlistRepository.save(playlist);
        log.info("Created playlist: {}", playlist.getName());
    }

    @Override
    public void addVideoInPlaylist(Long playListId, Long videoId) {
        Playlist playlist = playlistRepository.findById(playListId).orElseThrow(
                () -> new RuntimeException("Playlist not found")
        );
        Video video = videoRepository.findById(videoId).orElseThrow(() -> new RuntimeException("Video not found"));
        playlist.addVideo(video);
        playlistRepository.save(playlist);
        log.info("Added video {} to playlist: {}", video.getName(), playlist.getName());
    }

}
