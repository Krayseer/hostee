package ru.anykeyers.videoservice.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "need to enter username")
    private String username;

    /**
     * Пароль
     */
    @NotBlank(message = "need to enter password")
    private String password;

    /**
     * Почтовый адрес
     */
    @Email(message = "bad email")
    @NotBlank(message = "need to enter email")
    private String email;

}
