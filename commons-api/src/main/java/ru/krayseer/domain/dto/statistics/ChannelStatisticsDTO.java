package ru.krayseer.domain.dto.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO с данными о статистике канала
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChannelStatisticsDTO {

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
