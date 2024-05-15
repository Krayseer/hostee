package ru.anykeyers.videoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anykeyers.videoservice.domain.Channel;
import ru.anykeyers.videoservice.domain.Video;

import java.util.List;

/**
 * Репозиторий для работы с {@link Video}
 */
@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    void deleteByVideoUuid(String uuid);

    /**
     * Получить список видео канала
     *
     * @param channel канал
     */
    List<Video> findByChannel(Channel channel);

    /**
     * Получить видео по идентификатору
     *
     * @param videoUuid идентификатор видео
     */
    Video findByVideoUuid(String videoUuid);

}
