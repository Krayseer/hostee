package ru.anykeyers.videoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anykeyers.videoservice.domain.channel.Channel;
import ru.anykeyers.videoservice.domain.user.User;

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
    Channel findChannelByUser(User user);

    /**
     * Получить канал по пользователю пользователя
     *
     * @param username имя пользователя
     */
    Channel findChannelByUserUsername(String username);

    /**
     * Найти канал по имени
     *
     * @param name имя канала
     */
    Channel findChannelByName(String name);

    /**
     * Найти канал по его  id
     *
     * @param id id канала
     */
    Channel findChannelById(Long id);

}
