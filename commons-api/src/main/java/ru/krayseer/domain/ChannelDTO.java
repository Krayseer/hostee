package ru.krayseer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO с данными о канале
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChannelDTO {

    /**
     * Идентификатор
     */
    private Long id;

    /**
     * Пользователь
     */
    private UserDTO user;

    /**
     * Название
     */
    private String name;

    /**
     * Описание
     */
    private String description;

    /**
     * URL фотографии канала
     */
    private String photoUrl;

}
