package ru.anykeyers.notificationservice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.Instant;

/**
 * Пуш уведомление
 */
@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PushNotification {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Идентификатор пользователя
     */
    private Long userId;

    /**
     * Данные уведомления
     */
    private String content;

    /**
     * Время создания уведомления
     */
    private Instant createdAt;

}
