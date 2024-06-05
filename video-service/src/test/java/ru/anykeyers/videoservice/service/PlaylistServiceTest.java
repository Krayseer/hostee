package ru.anykeyers.videoservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.videoservice.domain.channel.Channel;
import ru.anykeyers.videoservice.domain.channel.ChannelRequest;
import ru.anykeyers.videoservice.domain.playlist.Playlist;
import ru.anykeyers.videoservice.domain.playlist.PlaylistRequest;
import ru.anykeyers.videoservice.domain.user.User;
import ru.anykeyers.videoservice.domain.video.Video;
import ru.anykeyers.videoservice.exception.ChannelNotExistsException;
import ru.anykeyers.videoservice.exception.PlaylistNotFoundException;
import ru.anykeyers.videoservice.exception.VideoNotFoundException;
import ru.anykeyers.videoservice.repository.ChannelRepository;
import ru.anykeyers.videoservice.repository.PlaylistRepository;
import ru.anykeyers.videoservice.repository.VideoRepository;
import ru.anykeyers.videoservice.service.impl.PlaylistServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для {@link PlaylistService}
 */
@ExtendWith(MockitoExtension.class)
class PlaylistServiceTest {

    @Mock
    private PlaylistRepository playlistRepository;

    @Mock
    private ChannelRepository channelRepository;

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private PlaylistServiceImpl playlistService;

    @Captor
    private ArgumentCaptor<Playlist> playlistCaptor;

    private final User user = User.builder().username("test-user").build();

    /**
     * Тест создания плейлиста пользователю, у которого нет канала
     */
    @Test
    void createPlaylistNotExistsChannel() {
        Mockito.when(channelRepository.findChannelByUserUsername("test-user")).thenReturn(Optional.empty());

        ChannelNotExistsException exception = Assertions.assertThrows(
                ChannelNotExistsException.class, () -> playlistService.createPlaylist("test-user", Mockito.mock(PlaylistRequest.class))
        );

        Assertions.assertEquals("Channel not exists for user: test-user", exception.getMessage());
    }

    /**
     * Тест успешного создания плейлиста в канале пользователя
     */
    @Test
    void createPlaylist() {
        Channel channel = Channel.builder().user(user).build();
        PlaylistRequest playlistRequest = new PlaylistRequest("test-title", "test-description");

        Mockito.when(channelRepository.findChannelByUserUsername("test-user")).thenReturn(Optional.of(channel));

        playlistService.createPlaylist("test-user", playlistRequest);

        Mockito.verify(playlistRepository, Mockito.times(1)).save(playlistCaptor.capture());
        Playlist playlist = playlistCaptor.getValue();
        Assertions.assertEquals("test-title", playlist.getName());
        Assertions.assertEquals("test-description", playlist.getDescription());
        Assertions.assertEquals(channel, playlist.getChannel());
    }

    /**
     * Тест добавления видео в несуществующий плейлист
     */
    @Test
    void addVideoInNotExistingPlaylist() {
        Long playlistId = 1L;

        Mockito.when(playlistRepository.findById(playlistId)).thenReturn(Optional.empty());

        PlaylistNotFoundException exception = Assertions.assertThrows(
                PlaylistNotFoundException.class, () -> playlistService.addVideoInPlaylist(1L, 1L)
        );

        Assertions.assertEquals("Playlist with id 1 not found", exception.getMessage());
    }

    /**
     * Тест добавления несуществующего видео в плейлист
     */
    @Test
    void addNotExistingVideoInPlaylist() {
        Long videoId = 1L;

        Mockito.when(playlistRepository.findById(2L)).thenReturn(Optional.ofNullable(Mockito.mock(Playlist.class)));
        Mockito.when(videoRepository.findById(videoId)).thenReturn(Optional.empty());

        VideoNotFoundException exception = Assertions.assertThrows(
                VideoNotFoundException.class, () -> playlistService.addVideoInPlaylist(2L, 1L)
        );

        Assertions.assertEquals("Video with id 1 not found", exception.getMessage());
    }

    /**
     * Тест успешного добавления видео в плейлист
     */
    @Test
    void addVideoInPlaylist() {
        List<Video> videos = new ArrayList<>();
        videos.add(new Video());
        Playlist playlist = Playlist.builder().id(1L).videos(videos).build();
        Video video = Video.builder().id(3L).build();

        Mockito.when(playlistRepository.findById(1L)).thenReturn(Optional.ofNullable(playlist));
        Mockito.when(videoRepository.findById(3L)).thenReturn(Optional.ofNullable(video));

        playlistService.addVideoInPlaylist(1L, 3L);

        Mockito.verify(playlistRepository, Mockito.times(1)).save(playlistCaptor.capture());
        Playlist captorPlaylist = playlistCaptor.getValue();
        Assertions.assertEquals(2, captorPlaylist.getVideos().size());
    }

}