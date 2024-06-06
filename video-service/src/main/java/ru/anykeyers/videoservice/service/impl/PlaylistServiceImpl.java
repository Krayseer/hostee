package ru.anykeyers.videoservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.anykeyers.videoservice.domain.channel.Channel;
import ru.anykeyers.videoservice.domain.playlist.Playlist;
import ru.anykeyers.videoservice.domain.playlist.PlaylistMapper;
import ru.anykeyers.videoservice.domain.video.Video;
import ru.anykeyers.videoservice.domain.playlist.PlaylistRequest;
import ru.anykeyers.videoservice.domain.playlist.PlaylistDTO;
import ru.anykeyers.videoservice.exception.ChannelNotExistsException;
import ru.anykeyers.videoservice.exception.PlaylistNotFoundException;
import ru.anykeyers.videoservice.exception.VideoNotFoundException;
import ru.anykeyers.videoservice.repository.ChannelRepository;
import ru.anykeyers.videoservice.repository.PlaylistRepository;
import ru.anykeyers.videoservice.repository.VideoRepository;
import ru.anykeyers.videoservice.service.PlaylistService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    private final VideoRepository videoRepository;

    private final PlaylistRepository playlistRepository;

    private final ChannelRepository channelRepository;

    @Override
    public List<PlaylistDTO> getPlaylists(String username) {
        return playlistRepository.findByChannelUserUsername(username).stream()
                .map(PlaylistMapper::createDTO)
                .toList();
    }

    @Override
    public PlaylistDTO getPlaylist(Long id) {
        Playlist playlist = playlistRepository.findById(id).orElseThrow(
                () -> new PlaylistNotFoundException(id)
        );
        return PlaylistMapper.createDTO(playlist);
    }

    @Override
    public void createPlaylist(String username, PlaylistRequest playlistRequest) {
        Channel channel = channelRepository.findChannelByUserUsername(username).orElseThrow(
                () -> new ChannelNotExistsException(username)
        );
        Playlist playlist = PlaylistMapper.createPlaylist(playlistRequest, channel);
        playlistRepository.save(playlist);
        log.info("Created playlist: {}", playlist.getName());
    }

    @Override
    public void addVideoInPlaylist(Long playListId, Long videoId) {
        Playlist playlist = playlistRepository.findById(playListId).orElseThrow(
                () -> new PlaylistNotFoundException(playListId)
        );
        Video video = videoRepository.findById(videoId).orElseThrow(
                () -> new VideoNotFoundException(videoId)
        );
        playlist.addVideo(video);
        playlistRepository.save(playlist);
        log.info("Added video {} to playlist: {}", video.getName(), playlist.getName());
    }

}
