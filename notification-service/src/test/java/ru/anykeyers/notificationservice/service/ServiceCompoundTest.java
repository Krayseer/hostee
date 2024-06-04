package ru.anykeyers.notificationservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.anykeyers.notificationservice.service.impl.EmailNotificationService;
import ru.anykeyers.notificationservice.service.impl.PushNotificationService;
import ru.krayseer.domain.UserSettingDTO;

import java.util.Collections;
import java.util.List;

/**
 * Тестирование класса {@link ServiceCompound}
 */
@ExtendWith(MockitoExtension.class)
class ServiceCompoundTest {

    @Mock
    private EmailNotificationService emailNotificationService;

    @Mock
    private PushNotificationService pushNotificationService;

    @InjectMocks
    private ServiceCompound serviceCompound;

    /**
     * Проверка получения сервисов в зависимости от настроек
     *
     * <ul>
     *     <li>При всех отрицательных настройках = пустой список</li>
     *     <li>Только сервис отправки push уведомлений</li>
     *     <li>Только сервис отправки email уведомлений</li>
     *     <li>Список с сервисом отправки push и email уведомлений</li>
     * </ul>
     */
    @Test
    void getNotificationServices() {
        UserSettingDTO notificationSetting = new UserSettingDTO(false, false);
        Assertions.assertEquals(Collections.emptyList(), serviceCompound.getNotificationServices(notificationSetting));
        notificationSetting.setPushEnabled(true);
        Assertions.assertEquals(
                Collections.singletonList(pushNotificationService),
                serviceCompound.getNotificationServices(notificationSetting)
        );
        notificationSetting.setPushEnabled(false);
        notificationSetting.setEmailEnabled(true);
        Assertions.assertEquals(
                Collections.singletonList(emailNotificationService),
                serviceCompound.getNotificationServices(notificationSetting)
        );
        notificationSetting.setPushEnabled(true);
        notificationSetting.setEmailEnabled(true);
        Assertions.assertEquals(
                List.of(pushNotificationService, emailNotificationService),
                serviceCompound.getNotificationServices(notificationSetting)
        );
    }

}