package ru.anykeyers.videoservice.domain.setting;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

/**
 * Настройки уведомлений
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationSetting {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Разрешена отправка push уведомлений
     */
    private boolean pushEnabled;

    /**
     * Разрешена отправка уведомлений на email
     */
    private boolean emailEnabled;

}
