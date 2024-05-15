package ru.anykeyers.videoservice.domain.playlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO с данными для добавления видео в плейлист
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistVideoRequest {

    /**
     * Идентификатор канала
     */
    private Long playlistId;

    /**
     * Идентификатор видео
     */
    private Long videoId;

}
