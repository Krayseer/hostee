package ru.anykeyers.videoservice;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.anykeyers.videoservice.domain.User;

/**
 * DAO для работы с таблицей пользователей
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Получить пользователя по имени пользователя
     *
     * @param username имя пользователя
     */
    User findByUsername(String username);

}
