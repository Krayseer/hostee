package ru.anykeyers.videoservice.service;

import ru.anykeyers.videoservice.domain.playlist.PlaylistRequest;
import ru.anykeyers.videoservice.domain.playlist.PlaylistDTO;

import java.util.List;

/**
 * Сервис обработки плейлистов
 */
public interface PlaylistService {

    /**
     * Получить список плейлистов пользователя
     *
     * @param username имя пользователя
     */
    List<PlaylistDTO> getPlaylists(String username);

    /**
     * Получить список видео
     *
     * @param id идентификатор плейлиста
     */
    PlaylistDTO getPlaylist(Long id);

    /**
     * Создать плейлист
     *
     * @param username          имя пользователя
     * @param playlistRequest   данные о плейлисте
     */
    void createPlaylist(String username, PlaylistRequest playlistRequest);

    /**
     * Добавить видео в плейлист
     *
     * @param playListId    идентификатор плейлиста
     * @param videoId       идентификатор видео
     */
    void addVideoInPlaylist(Long playListId, Long videoId);
}
