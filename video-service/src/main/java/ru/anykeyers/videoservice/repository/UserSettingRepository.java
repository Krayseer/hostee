package ru.anykeyers.videoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anykeyers.videoservice.domain.User;
import ru.anykeyers.videoservice.domain.setting.UserSetting;

@Repository
public interface UserSettingRepository extends JpaRepository<UserSetting, Long> {

    /**
     * Получить настройки пользователя
     *
     * @param user пользователь
     */
    UserSetting findByUser(User user);

}
