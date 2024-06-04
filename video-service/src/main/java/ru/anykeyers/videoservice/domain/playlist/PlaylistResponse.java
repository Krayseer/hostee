package ru.anykeyers.videoservice.domain.playlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.anykeyers.videoservice.domain.video.Video;

import java.util.List;

/**
 * Ответ с видеороликами плейлиста
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistResponse {

    /**
     * Список видеороликов
     */
    private List<Video> videos;

}
