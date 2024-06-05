package ru.anykeyers.videoservice.domain.playlist;

import ru.anykeyers.videoservice.domain.channel.Channel;
import ru.anykeyers.videoservice.domain.video.VideoDTO;
import ru.anykeyers.videoservice.domain.video.VideoMapper;

import java.util.List;

/**
 * Маппер по обработке плейлиста
 */
public class PlaylistMapper {

    /**
     * Создать плейлист
     *
     * @param playlistRequest   данные о плейлисте
     * @param channel           канал
     */
    public static Playlist createPlaylist(PlaylistRequest playlistRequest, Channel channel) {
        return Playlist.builder()
                .name(playlistRequest.getName())
                .description(playlistRequest.getDescription())
                .channel(channel)
                .build();
    }

    /**
     * Создать DTO с данными о плейлисте
     *
     * @param playlist плейлист
     */
    public static PlaylistDTO createDTO(Playlist playlist) {
        List<VideoDTO> videos = playlist.getVideos().stream().map(VideoMapper::createDTO).toList();
        return new PlaylistDTO(playlist.getId(), playlist.getName(), playlist.getDescription(), videos);
    }

}
