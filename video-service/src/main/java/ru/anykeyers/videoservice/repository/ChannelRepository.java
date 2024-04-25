package ru.anykeyers.videoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anykeyers.videoservice.domain.Channel;
import ru.anykeyers.videoservice.domain.User;

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
