package ru.anykeyers.videoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anykeyers.videoservice.domain.setting.NotificationSetting;

/**
 * DAO для работы с настройками уведомлений пользователей
 */
@Repository
public interface NotificationUserSettingRepository extends JpaRepository<NotificationSetting, Long> {
}
