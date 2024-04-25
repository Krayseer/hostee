package ru.anykeyers.videoservice.factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.anykeyers.videoservice.domain.Role;
import ru.anykeyers.videoservice.domain.User;
import ru.anykeyers.videoservice.domain.dto.RegisterDTO;

import java.util.Set;

/**
 * Тесты для фабрики {@link UserFactory}
 */
@ExtendWith(MockitoExtension.class)
class UserFactoryTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserFactory userFactory;

    /**
     * Тестирование создания пользователя из DTO с регистрационными данными
     */
    @Test
    void createUser() {
        RegisterDTO registerDTO = new RegisterDTO("user1", "pass", "email@email.com");
        Mockito.when(passwordEncoder.encode(Mockito.any())).thenReturn("encodePass");

        User user = userFactory.createUser(registerDTO);

        User expectedUser = new User(
                null, "user1", "encodePass", "email@email.com", Set.of(Role.USER)
        );
        Assertions.assertEquals(expectedUser.getUsername(), user.getUsername());
        Assertions.assertEquals(expectedUser.getPassword(), user.getPassword());
        Assertions.assertEquals(expectedUser.getEmail(), user.getEmail());
        Assertions.assertEquals(expectedUser.getRoles(), user.getRoles());
    }
}