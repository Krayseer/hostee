package ru.anykeyers.videoservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO с данными для регистрации пользователя
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

    /**
     * Имя пользователя
     */
    private String username;

    /**
     * Пароль
     */
    private String password;

}
