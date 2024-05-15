package ru.anykeyers.videoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anykeyers.videoservice.domain.History;
import ru.anykeyers.videoservice.domain.User;

/**
 * DAO для работы с историей
 */
@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    /**
     * Получить историю просмотров видео пользователя
     *
     * @param user пользователь
     */
    History findByUser(User user);

    /**
     * Получить историю просмотров видео пользователя
     *
     * @param username имя пользователя
     */
    History findByUserUsername(String username);

}
