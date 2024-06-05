package ru.anykeyers.videoservice.domain.video;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String uuid;

    /**
     * Название
     */
    private String name;

    /**
     * Описание
     */
    private String description;

}

