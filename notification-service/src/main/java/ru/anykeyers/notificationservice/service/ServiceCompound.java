package ru.anykeyers.notificationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.notificationservice.service.impl.EmailNotificationService;
import ru.anykeyers.notificationservice.service.impl.PushNotificationService;
import ru.krayseer.domain.dto.NotificationSettingDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Комплекс сервисов уведомлений
 */
@Service
@RequiredArgsConstructor
public class ServiceCompound {

    private final PushNotificationService pushNotificationService;

    private final EmailNotificationService emailNotificationService;

    /**
     * Получить сервисы уведомлений
     *
     * @param notificationSetting настройки уведомлений
     */
    public List<NotificationService> getNotificationServices(NotificationSettingDTO notificationSetting) {
        List<NotificationService> services = new ArrayList<>();
        if (notificationSetting.isPushEnabled()) {
            services.add(pushNotificationService);
        }
        if (notificationSetting.isEmailEnabled()) {
            services.add(emailNotificationService);
        }
        return services;
    }

}
