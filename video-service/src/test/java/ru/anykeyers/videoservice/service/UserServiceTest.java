package ru.anykeyers.videoservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import ru.anykeyers.videoservice.factory.UserFactory;
import ru.anykeyers.videoservice.repository.UserRepository;
import ru.anykeyers.videoservice.domain.Role;
import ru.anykeyers.videoservice.domain.User;
import ru.anykeyers.videoservice.domain.dto.AuthDTO;
import ru.anykeyers.videoservice.domain.dto.RegisterDTO;
import ru.anykeyers.videoservice.domain.dto.TokenDTO;
import ru.anykeyers.videoservice.exception.UserAlreadyExistsException;
import ru.anykeyers.videoservice.exception.UserNotFoundException;
import ru.anykeyers.videoservice.security.JwtService;
import ru.anykeyers.videoservice.service.impl.UserServiceImpl;

import java.util.Set;

/**
 * Тесты для серивса {@link UserService}
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserFactory userFactory;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserServiceImpl userService;

    /**
     * Тест на регистрацию пользователя уже с существующим именем пользователя
     */
    @Test
    void registerUserWithAlreadyExistsUsername() {
        RegisterDTO registerDTO = new RegisterDTO("user1", "pass", "email@email.com");
        Mockito.when(userRepository.findByUsername(registerDTO.getUsername())).thenReturn(new User());

        UserAlreadyExistsException userAlreadyExistsException = Assertions.assertThrows(
                UserAlreadyExistsException.class, () -> userService.registerUser(registerDTO)
        );

        Assertions.assertEquals(
                userAlreadyExistsException.getMessage(), "User with username 'user1' already exists"
        );
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any());
        Mockito.verify(jwtService, Mockito.never()).generateToken(Mockito.any());
    }

    /**
     * Тест успешной регистрации пользователя
     */
    @Test
    void registerUserSuccessfully() {
        RegisterDTO registerDTO = new RegisterDTO("user1", "pass", "email@email.com");
        User testUser = new User(
                0L, "user1", "pass", "email@email.com", Set.of(Role.USER)
        );
        Mockito.when(userRepository.findByUsername(registerDTO.getUsername())).thenReturn(null);
        Mockito.when(jwtService.generateToken("user1")).thenReturn("testToken");
        Mockito.when(userFactory.createUser(registerDTO)).thenReturn(testUser);

        TokenDTO tokenDTO = userService.registerUser(registerDTO);

        Mockito.verify(userRepository, Mockito.times(1)).save(testUser);
        Mockito.verify(jwtService, Mockito.times(1)).generateToken("user1");
        Assertions.assertEquals(new TokenDTO("testToken"), tokenDTO);
    }

    /**
     * Тест авторизации несуществующего пользователя
     */
    @Test
    void authNotExistsUser() {
        AuthDTO authDTO = new AuthDTO("user1", "pass");
        Mockito.when(userRepository.findByUsername(authDTO.getUsername())).thenReturn(null);

        UserNotFoundException userNotFoundException = Assertions.assertThrows(
                UserNotFoundException.class, () -> userService.authUser(authDTO)
        );

        Assertions.assertEquals(userNotFoundException.getMessage(), "User with username 'user1' not found");
        Mockito.verify(jwtService, Mockito.never()).generateToken(Mockito.any());
    }

    /**
     * Тест успешной авторизации пользователя
     */
    @Test
    void authUserSuccessfully() {
        AuthDTO authDTO = new AuthDTO("user1", "pass");
        Mockito.when(userRepository.findByUsername("user1")).thenReturn(new User());
        Mockito.when(jwtService.generateToken("user1")).thenReturn("testToken");

        TokenDTO tokenDTO = userService.authUser(authDTO);

        Mockito.verify(jwtService, Mockito.times(1)).generateToken("user1");
        Assertions.assertEquals(new TokenDTO("testToken"), tokenDTO);
    }

}