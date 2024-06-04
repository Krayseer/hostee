package ru.krayseer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO с данными о пользователе
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    /**
     * Идентификатор
     */
    private Long id;

    /**
     * Имя пользователя
     */
    private String username;

    /**
     * Почта
     */
    private String email;

    /**
     * Настройки уведомлений
     */
    private UserSettingDTO userSetting;

}
