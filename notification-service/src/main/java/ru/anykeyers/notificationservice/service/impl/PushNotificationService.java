package ru.anykeyers.notificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.anykeyers.notificationservice.PushNotification;
import ru.anykeyers.notificationservice.PushNotificationRepository;
import ru.anykeyers.notificationservice.service.NotificationService;
import ru.krayseer.domain.UserDTO;

import java.time.Instant;

/**
 * Реализация отправки push уведомлений
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PushNotificationService implements NotificationService {

    private final PushNotificationRepository pushNotificationRepository;

    @Override
    public void notify(UserDTO user, Object message) {
        PushNotification pushNotification = PushNotification.builder()
                .userId(user.getId())
                .content((String) message)
                .createdAt(Instant.now())
                .build();
        pushNotificationRepository.save(pushNotification);
        log.info("Send push notification {}", pushNotification);
    }

}
