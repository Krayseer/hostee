package ru.anykeyers.videoservice.domain.setting;

import jakarta.persistence.*;
import lombok.*;
import ru.anykeyers.videoservice.domain.User;

/**
 * Настройки пользователя
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSetting {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Пользователь, которому принадлежат настройки
     */
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Настройки уведомлений
     */
    @OneToOne
    @JoinColumn(name = "notification_setting_id")
    private NotificationSetting notificationSetting;

}
