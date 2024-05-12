package ru.anykeyers.notificationservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO для работы с пуш уведомлениями
 */
@Repository
public interface PushNotificationRepository extends JpaRepository<PushNotification, Long> {
}
