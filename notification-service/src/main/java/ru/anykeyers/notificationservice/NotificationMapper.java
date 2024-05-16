package ru.anykeyers.notificationservice;

import ru.anykeyers.notificationservice.domain.PushNotification;
import ru.krayseer.domain.dto.PushNotificationDTO;

public class NotificationMapper {

    public static PushNotificationDTO createDTOFromPushNotification(PushNotification pushNotification) {
        return PushNotificationDTO.builder()
                .content(pushNotification.getContent())
                .createdAt(pushNotification.getCreatedAt())
                .build();
    }

}
