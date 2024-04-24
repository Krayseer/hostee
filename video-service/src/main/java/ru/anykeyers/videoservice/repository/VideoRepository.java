package ru.anykeyers.videoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anykeyers.videoservice.domain.Video;

/**
 * Репозиторий для работы с {@link Video}
 */
@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
}
