package ru.anykeyers.videoservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.videoservice.domain.dto.AuthDTO;
import ru.anykeyers.videoservice.domain.dto.RegisterDTO;
import ru.anykeyers.videoservice.domain.dto.TokenDTO;
import ru.anykeyers.videoservice.domain.Role;
import ru.krayseer.domain.dto.NotificationSettingDTO;
import ru.krayseer.domain.dto.PushNotificationDTO;
import ru.krayseer.domain.dto.UserDTO;
import ru.anykeyers.videoservice.service.UserService;

import java.security.Principal;
import java.util.List;

/**
 * REST контроллер для работы с пользователями
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public UserDTO getUser(Principal user) {
        return userService.getUser(user.getName());
    }

    @GetMapping("/{username}")
    public UserDTO getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/sign-up")
    public TokenDTO registerUser(@RequestBody @Valid RegisterDTO registerDTO) {
        return userService.registerUser(registerDTO);
    }

    @PostMapping("/sign-in")
    public TokenDTO loginUser(@RequestBody @Valid AuthDTO authDTO) {
        return userService.authUser(authDTO);
    }

    @PostMapping("/notification-setting")
    public void setNotificationSetting(@RequestBody NotificationSettingDTO notificationSetting,
                                       Principal user) {
        userService.setNotificationSetting(user.getName(), notificationSetting);
    }

    @PostMapping("/block/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserDTO blockUser(@PathVariable Long id) {
        return userService.blockUser(id);
    }

    @PostMapping("/set-roles/{username}")
    public void setUserRoles(@PathVariable String username,
                             @RequestBody List<Role> roles) {
        userService.setUserRoles(username, roles);
    }

    @GetMapping("/notifications")
    public List<PushNotificationDTO> getUserPushNotifications(Principal principal) {
        return userService.getUserPushNotifications(principal.getName());
    }

}
