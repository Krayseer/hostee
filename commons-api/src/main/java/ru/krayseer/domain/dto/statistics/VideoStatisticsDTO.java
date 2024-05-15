package ru.krayseer.domain.dto.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO с данными о статистике видео
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoStatisticsDTO {

    /**
     * Идентификатор видео
     */
    private String videoUuid;

    /**
     * Количество просмотров
     */
    private long countWatches;

}
