package ru.anykeyers.videoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anykeyers.videoservice.domain.channel.Channel;
import ru.anykeyers.videoservice.domain.video.Video;

import java.util.List;

/**
 * Репозиторий для работы с {@link Video}
 */
@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

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

    /**
     * Удалить видео по идентификатору
     *
     * @param uuid идентификатор видео
     */
    void deleteByVideoUuid(String uuid);

}
