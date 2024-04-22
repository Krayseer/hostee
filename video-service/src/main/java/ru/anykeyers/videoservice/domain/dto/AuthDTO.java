package ru.anykeyers.videoservice.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO с данными для авторизации пользователя
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDTO {

    /**
     * Имя пользователя
     */
    @NotBlank(message = "you need to enter username")
    private String username;

    /**
     * Пароль
     */
    @NotBlank(message = "you need to enter password")
    private String password;

}
