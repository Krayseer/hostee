package ru.anykeyers.videoservice.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.krayseer.domain.UserSettingDTO;
import ru.krayseer.domain.UserDTO;

import java.util.Set;

/**
 * Фабрика для создания пользователя
 */
@Component
@RequiredArgsConstructor
public final class UserMapper {

    /**
     * Создать пользователя на основе данных из DTO
     *
     * @param registerDTO данные для создания пользователя
     */
    public static User createUser(RegisterDTO registerDTO, PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(registerDTO.getUsername())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .email(registerDTO.getEmail())
                .blocked(false)
                .userSetting(new UserSetting())
                .roles(Set.of(Role.USER))
                .build();
    }

    /**
     * Создать DTO с данными о пользователе
     *
     * @param user пользователь
     */
    public static UserDTO createDTO(User user) {
        UserSettingDTO userSetting = user.getUserSetting() == null
                ? null
                : new UserSettingDTO(
                        user.getUserSetting().isPushEnabled(), user.getUserSetting().isEmailEnabled()
        );
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), userSetting, user.isBlocked(), user.getRoles().stream().map(Enum::toString).toList());
    }

}
