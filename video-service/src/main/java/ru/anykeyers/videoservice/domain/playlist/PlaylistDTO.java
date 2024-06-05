package ru.anykeyers.videoservice.domain.playlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.anykeyers.videoservice.domain.video.Video;
import ru.anykeyers.videoservice.domain.video.VideoDTO;

import java.util.List;

/**
 * Ответ с видеороликами плейлиста
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistDTO {

    /**
     * Идентификатор
     */
    private Long id;

    /**
     * Название
     */
    private String name;

    /**
     * Описание
     */
    private String description;

    /**
     * Список видеороликов
     */
    private List<VideoDTO> videos;

}
