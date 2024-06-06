package ru.anykeyers.videoservice.domain.video;

import jakarta.persistence.*;
import lombok.*;
import ru.anykeyers.videoservice.domain.channel.Channel;

import java.time.Instant;

/**
 * Сущность видео для БД
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Video {

    /**
     * Идентификатор видео
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Название видео
     */
    @Column(name = "name")
    private String name;

    /**
     * Описание видео
     */
    @Column(name = "description")
    private String description;

    /**
     * Ссылка на канал, которому принадлежит видео
     */
    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    /**
     * Идентификатор видео в хранилище
     */
    @Column(name = "video_uuid")
    private String videoUuid;

    /**
     * Идентификатор превью видео
     */
    private String previewUuid;

    /**
     * Статус загрузки видео в хранилище
     */
    @Enumerated(EnumType.STRING)
    private UploadStatus uploadStatus;

    /**
     * Время загрузки
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

}
