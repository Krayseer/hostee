package ru.krayseer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO с данными о настройках уведомлений
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSettingDTO {

    /**
     * Разрешены push уведомления
     */
    private boolean pushEnabled;

    /**
     * Разрешены email уведомления
     */
    private boolean emailEnabled;

}
