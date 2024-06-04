//package ru.anykeyers.videoservice.factory;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import ru.anykeyers.videoservice.domain.user.Role;
//import ru.anykeyers.videoservice.domain.user.User;
//import ru.anykeyers.videoservice.domain.user.RegisterDTO;
//import ru.anykeyers.videoservice.domain.user.UserMapper;
//
//import java.util.Set;
//
///**
// * Тесты для фабрики {@link UserMapper}
// */
//@ExtendWith(MockitoExtension.class)
//class UserMapperTest {
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @InjectMocks
//    private UserMapper userMapper;
//
//    /**
//     * Тестирование создания пользователя из DTO с регистрационными данными
//     */
//    @Test
//    void createUser() {
//        RegisterDTO registerDTO = new RegisterDTO("user1", "pass", "email@email.com");
//        Mockito.when(passwordEncoder.encode(Mockito.any())).thenReturn("encodePass");
//
//        User user = userMapper.createUser(registerDTO);
//
//        User expectedUser = new User(
//                null, "user1", "encodePass", "email@email.com", false, Set.of(Role.USER)
//        );
//        Assertions.assertEquals(expectedUser.getUsername(), user.getUsername());
//        Assertions.assertEquals(expectedUser.getPassword(), user.getPassword());
//        Assertions.assertEquals(expectedUser.getEmail(), user.getEmail());
//        Assertions.assertEquals(expectedUser.getRoles(), user.getRoles());
//    }
//}