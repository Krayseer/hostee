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
     * Получить канал по пользователю пользователя
     *
     * @param user имя пользователя
     */
    Optional<Channel> findChannelByUser(User user);

    /**
     * Получить канал по пользователю пользователя
     *
     * @param username имя пользователя
     */
    Optional<Channel> findChannelByUserUsername(String username);

    /**
     * Найти канал по имени
     *
     * @param name имя канала
     */
    boolean existsChannelByName(String name);

    /**
     * Найти канал по его  id
     *
     * @param id id канала
     */
    Optional<Channel> findChannelById(Long id);

}
