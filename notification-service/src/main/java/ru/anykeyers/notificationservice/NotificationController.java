package ru.anykeyers.notificationservice;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.anykeyers.notificationservice.service.impl.PushNotificationService;
import ru.krayseer.domain.dto.PushNotificationDTO;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {


    private final PushNotificationService pushNotificationService;

    public NotificationController(@Qualifier("pushNotificationService") PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    @GetMapping("/{userId}")
    public List<PushNotificationDTO> getUserPushNotifications(@PathVariable Long userId) {
        return pushNotificationService.getNotificationsByUserId(userId);
    }
}
