package ru.anykeyers.notificationservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anykeyers.notificationservice.domain.PushNotification;

import java.util.List;

/**
 * DAO для работы с пуш уведомлениями
 */
@Repository
public interface PushNotificationRepository extends JpaRepository<PushNotification, Long> {

    List<PushNotification> findAllByUserId(long receiverId);

}
