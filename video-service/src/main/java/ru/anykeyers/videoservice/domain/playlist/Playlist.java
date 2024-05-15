package ru.anykeyers.videoservice.domain.playlist;

import jakarta.persistence.*;
import lombok.*;
import ru.anykeyers.videoservice.domain.Channel;
import ru.anykeyers.videoservice.domain.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Плейлист
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Playlist {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
     * Канал, которому принадлежит плейлист
     */
    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    /**
     * Список видеороликов
     */
    @ManyToMany
    @JoinTable(
            name = "playlist_videos",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "video_id")
    )
    private List<Video> videos;

    /**
     * Добавить видео
     *
     * @param video видео
     */
    public void addVideo(Video video) {
        if (videos == null) {
            videos = new ArrayList<>();
        }
        videos.add(video);
    }

}
