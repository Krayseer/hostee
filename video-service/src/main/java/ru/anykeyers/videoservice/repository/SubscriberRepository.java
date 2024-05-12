package ru.anykeyers.videoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anykeyers.videoservice.domain.Subscriber;

import java.util.List;

/**
 * DAO для работы с подписчиками
 */
@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

    /**
     * Получить подписиков канала
     *
     * @param id идентификатор канала
     */
    List<Subscriber> findByChannelId(Long id);

}
