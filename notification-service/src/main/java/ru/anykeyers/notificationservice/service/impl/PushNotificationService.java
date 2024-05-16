package ru.anykeyers.notificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.anykeyers.notificationservice.NotificationMapper;
import ru.anykeyers.notificationservice.domain.PushNotification;
import ru.anykeyers.notificationservice.PushNotificationRepository;
import ru.anykeyers.notificationservice.service.NotificationService;
import ru.krayseer.domain.dto.PushNotificationDTO;
import ru.krayseer.domain.dto.UserDTO;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<PushNotificationDTO> getNotificationsByUserId(Long userId) {
        List<PushNotification> pushNotifications = pushNotificationRepository.findAllByUserId(userId);
        return pushNotifications == null
                ? new ArrayList<>()
                : pushNotifications.stream()
                    .map(NotificationMapper::createDTOFromPushNotification)
                    .collect(Collectors.toList());
    }

}
