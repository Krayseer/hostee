package ru.anykeyers.statistics.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

/**
 * Статистика канала
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Channel {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Идентификатор канала
     */
    private Long channelId;

    /**
     * Количество просмотров главной страницы канала
     */
    private long countWatches;

    /**
     * Количество подписчиков
     */
    private long subscribersCount;

}
