package ru.anykeyers.videoservice.domain.video;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.krayseer.domain.statistics.VideoStatisticsDTO;

/**
 * DTO с данными о видео
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoDTO {

    /**
     * Идентификатор видео
     */
    private Long id;

    /**
     * Идентификатор видео в хранилище
     */
    private String uuid;

    /**
     * Название
     */
    private String name;

    /**
     * Описание
     */
    private String description;

    /**
     * Время загрузки
     */
    private String createdAt;

    /**
     * Статистика канала
     */
    private VideoStatisticsDTO statistics;

}

