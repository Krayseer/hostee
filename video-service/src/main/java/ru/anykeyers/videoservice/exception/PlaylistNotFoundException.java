package ru.anykeyers.videoservice.exception;

/**
 * Исключение, что не существует плейлист
 */
public class PlaylistNotFoundException extends RuntimeException {

    public PlaylistNotFoundException(Long id) {
        super("Playlist with id " + id + " not found");
    }

}
