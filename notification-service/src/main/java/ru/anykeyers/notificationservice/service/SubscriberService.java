package ru.anykeyers.notificationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.krayseer.domain.SubscriberDTO;
import ru.krayseer.domain.UserDTO;

/**
 * Сервис обработки подписчиков
 */
@Service
@RequiredArgsConstructor
public class SubscriberService {

    private final ServiceCompound serviceCompound;

    /**
     * Уведомить о новом подписчике на канале
     *
     * @param subscriber данные о подписчике
     */
    public void notifySubscribeChannel(SubscriberDTO subscriber) {
        UserDTO user = subscriber.getChannel().getUser();
        String content = String.format("""
                У вас появился новый подписчик!
                Канал: %s
                """, subscriber.getSubscriberChannel().getName());
        serviceCompound
                .getNotificationServices(user.getUserSetting())
                .forEach(service -> service.notify(user, content));
    }

}
