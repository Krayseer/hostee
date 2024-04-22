package ru.anykeyers.videoservice;

import ru.anykeyers.videoservice.domain.Role;
import ru.anykeyers.videoservice.domain.User;
import ru.anykeyers.videoservice.domain.dto.RegisterDTO;

import java.util.Set;

/**
 * Фабрика для создания пользователя
 */
public final class UserFactory {

    /**
     * Создать пользователя на основе данных из DTO
     *
     * @param registerDTO данные для создания пользователя
     */
    public static User createUser(RegisterDTO registerDTO) {
        return User.builder()
                .username(registerDTO.getUsername())
                .password(registerDTO.getPassword())
                .role(Set.of(Role.USER))
                .build();
    }

}
