package ru.anykeyers.videoservice.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * Подписчик
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscriber {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Канал (подписчик)
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Channel subscriberChannel;

    /**
     * Идентификатор канала
     */
    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

}
