package ru.anykeyers.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anykeyers.statistics.domain.entity.Video;

/**
 * DAO для работы со  статистикой видео
 */
@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    /**
     * Получить видео
     *
     * @param uuid идентификатор видео
     */
    Video findByUuid(String uuid);

}
