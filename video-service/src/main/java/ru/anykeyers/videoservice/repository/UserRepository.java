package ru.anykeyers.videoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anykeyers.videoservice.domain.user.User;

/**
 * DAO для работы с таблицей пользователей
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Получить пользователя по имени пользователя
     *
     * @param username имя пользователя
     */
    User findByUsername(String username);

}
