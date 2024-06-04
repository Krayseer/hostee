package ru.anykeyers.notificationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.notificationservice.service.impl.EmailNotificationService;
import ru.anykeyers.notificationservice.service.impl.PushNotificationService;
import ru.krayseer.domain.UserSettingDTO;

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
     * @param userSetting настройки уведомлений
     */
    public List<NotificationService> getNotificationServices(UserSettingDTO userSetting) {
        List<NotificationService> services = new ArrayList<>();
        if (userSetting.isPushEnabled()) {
            services.add(pushNotificationService);
        }
        if (userSetting.isEmailEnabled()) {
            services.add(emailNotificationService);
        }
        return services;
    }

}
