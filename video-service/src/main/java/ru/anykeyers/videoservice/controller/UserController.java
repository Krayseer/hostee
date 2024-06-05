package ru.anykeyers.videoservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.videoservice.domain.dto.AuthDTO;
import ru.anykeyers.videoservice.domain.user.RegisterDTO;
import ru.anykeyers.videoservice.domain.dto.TokenDTO;
import ru.anykeyers.videoservice.domain.user.Role;
import ru.krayseer.domain.UserSettingDTO;
import ru.krayseer.domain.UserDTO;
import ru.anykeyers.videoservice.service.UserService;

import java.security.Principal;
import java.util.List;

@Tag(name = "Обработка пользователей")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Получить информацию об авторизованном пользователе")
    @GetMapping
    public UserDTO getUser(Principal user) {
        return userService.getUser(user.getName());
    }

    @Operation(summary = "Получить информацию о пользователе")
    @GetMapping("/{username}")
    public UserDTO getUser(
            @Parameter(description = "Имя пользователя") @PathVariable String username
    ) {
        return userService.getUser(username);
    }

    @Operation(summary = "Получить информацию обо всех пользователях")
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Зарегистрировать пользователя")
    @PostMapping("/sign-up")
    public TokenDTO registerUser(
            @Parameter(description = "Данные о пользователе") @RequestBody @Valid RegisterDTO registerDTO
    ) {
        return userService.registerUser(registerDTO);
    }

    @Operation(summary = "Авторизоваться")
    @PostMapping("/sign-in")
    public TokenDTO loginUser(
            @Parameter(description = "Авторизационные данные") @RequestBody @Valid AuthDTO authDTO
    ) {
        return userService.authUser(authDTO);
    }

    @Operation(summary = "Установить настройки пользователя")
    @PostMapping("/user-setting")
    public void setNotificationSetting(
            @Parameter(description = "Настройки") @RequestBody UserSettingDTO notificationSetting,
            Principal user
    ) {
        userService.setNotificationSetting(user.getName(), notificationSetting);
    }

    @Operation(summary = "Заблокировать пользователя")
    @PostMapping("/block/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserDTO blockUser(
            @Parameter(description = "Идентификатор пользователя") @PathVariable Long id
    ) {
        return userService.blockUser(id);
    }

    @PostMapping("/unblock/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserDTO unblockUser(@PathVariable Long id) {
        return userService.unblockUser(id);
    }

    @Operation(summary = "Установить роли пользователю")
    @PostMapping("/set-roles/{username}")
    public void setUserRoles(
            @Parameter(description = "Имя пользователя") @PathVariable String username,
            @Parameter(description = "Список ролей") @RequestBody List<Role> roles
    ) {
        userService.setUserRoles(username, roles);
    }

}
