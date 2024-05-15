package ru.anykeyers.videoservice.domain.playlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO с данными о плейлисте
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistRequest {

    /**
     * Название
     */
    private String name;

    /**
     * Описание
     */
    private String description;

}
