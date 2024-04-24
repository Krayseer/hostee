package ru.anykeyers.videoservice.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * Сущность канала
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Channel {

    /**
     * Id канала
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Ссылка на владельца канала
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "users_id")
    private User user;

    /**
     * Имя канала
     */
    @Column(name = "name")
    private String name;

    /**
     * Описание канала
     */
    @Column(name = "description")
    private String description;
}
