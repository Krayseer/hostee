package ru.anykeyers.videoservice.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.anykeyers.videoservice.domain.Role;
import ru.anykeyers.videoservice.domain.User;
import ru.anykeyers.videoservice.domain.dto.RegisterDTO;
import ru.anykeyers.videoservice.domain.dto.UserDTO;

import java.util.Set;

/**
 * Фабрика для создания пользователя
 */
@Component
@RequiredArgsConstructor
public final class UserFactory {

    private final PasswordEncoder passwordEncoder;

    /**
     * Создать пользователя на основе данных из DTO
     *
     * @param registerDTO данные для создания пользователя
     */
    public User createUser(RegisterDTO registerDTO) {
        return User.builder()
                .username(registerDTO.getUsername())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .email(registerDTO.getEmail())
                .roles(Set.of(Role.USER))
                .build();
    }

    /**
     * Создать DTO с данными о пользователе
     *
     * @param user пользователь
     */
    public UserDTO createUserDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail());
    }

}
