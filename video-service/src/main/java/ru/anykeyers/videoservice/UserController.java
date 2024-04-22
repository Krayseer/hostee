package ru.anykeyers.videoservice;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.videoservice.domain.dto.AuthDTO;
import ru.anykeyers.videoservice.domain.dto.RegisterDTO;
import ru.anykeyers.videoservice.domain.dto.TokenDTO;
import ru.anykeyers.videoservice.service.UserService;

/**
 * REST контроллер для работы с пользователями
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    private TokenDTO registerUser(@RequestBody @Valid RegisterDTO registerDTO) {
        return userService.registerUser(registerDTO);
    }

    @PostMapping("/sign-in")
    private TokenDTO loginUser(@RequestBody @Valid AuthDTO authDTO) {
        return userService.authUser(authDTO);
    }

}
