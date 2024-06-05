package ru.anykeyers.videoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anykeyers.videoservice.domain.channel.Channel;
import ru.anykeyers.videoservice.domain.user.User;

import java.util.Optional;

/**
 * DAO для работы с таблицей каналов
 */
@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {

    /**
     * Найти канал
     *
     * @param id id канала
     */
    Optional<Channel> findChannelById(Long id);

    /**
     * Получить канал
     *
     * @param username имя пользователя
     */
    Optional<Channel> findChannelByUserUsername(String username);

    /**
     * Существует ли канал
     *
     * @param name имя канала
     */
    boolean existsChannelByName(String name);

}
