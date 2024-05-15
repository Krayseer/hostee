package ru.anykeyers.videoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anykeyers.videoservice.domain.playlist.Playlist;

/**
 * DAO для работы с плейлистами
 */
@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
