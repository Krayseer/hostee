package ru.anykeyers.videoservice.domain;

import jakarta.persistence.*;
import lombok.*;
import ru.anykeyers.videoservice.domain.user.User;
import ru.anykeyers.videoservice.domain.video.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * История просмотров
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class History {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Пользователь, которому принадлежит история
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Список просмотренных видеороликов
     */
    @ManyToMany
    @JoinTable(
            name = "history_videos",
            joinColumns = @JoinColumn(name = "history_id"),
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
