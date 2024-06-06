package ru.anykeyers.videoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anykeyers.videoservice.domain.playlist.Playlist;

import java.util.List;

/**
 * DAO для работы с плейлистами
 */
@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    /**
     * Получить список плейлистов канала пользователя
     *
     * @param username имя пользователя
     */
    List<Playlist> findByChannelUserUsername(String username);

}
