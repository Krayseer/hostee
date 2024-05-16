package ru.anykeyers.videoservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import ru.anykeyers.videoservice.domain.Role;
import ru.anykeyers.videoservice.domain.setting.NotificationSetting;
import ru.anykeyers.videoservice.domain.setting.UserSetting;
import ru.anykeyers.videoservice.repository.NotificationUserSettingRepository;
import ru.anykeyers.videoservice.repository.UserSettingRepository;
import ru.anykeyers.videoservice.service.remote.RemoteNotificationsService;
import ru.krayseer.domain.dto.NotificationSettingDTO;
import ru.krayseer.domain.dto.PushNotificationDTO;
import ru.krayseer.domain.dto.UserDTO;
import ru.anykeyers.videoservice.exception.UserAuthenticateException;
import ru.anykeyers.videoservice.repository.UserRepository;
import ru.anykeyers.videoservice.domain.User;
import ru.anykeyers.videoservice.domain.dto.AuthDTO;
import ru.anykeyers.videoservice.domain.dto.RegisterDTO;
import ru.anykeyers.videoservice.domain.dto.TokenDTO;
import ru.anykeyers.videoservice.factory.UserFactory;
import ru.anykeyers.videoservice.exception.UserAlreadyExistsException;
import ru.anykeyers.videoservice.exception.UserNotFoundException;
import ru.anykeyers.videoservice.security.JwtService;
import ru.anykeyers.videoservice.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса для работы с пользователями
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtService jwtService;

    private final UserFactory userFactory;

    private final UserRepository userRepository;

    private final UserSettingRepository userSettingRepository;

    private final AuthenticationManager authenticationManager;

    private final NotificationUserSettingRepository notificationUserSettingRepository;

    private final RemoteNotificationsService remoteNotificationsService;

    @Override
    public UserDTO getUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }
        return userFactory.createUserDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userFactory::createUserDTO).toList();
    }

    @Override
    public TokenDTO registerUser(RegisterDTO registerDTO) {
        if (userRepository.findByUsername(registerDTO.getUsername()) != null) {
            throw new UserAlreadyExistsException(registerDTO.getUsername());
        }
        User userFromDTO = userFactory.createUser(registerDTO);
        userRepository.save(userFromDTO);
        String jwtToken = jwtService.generateToken(registerDTO.getUsername());
        log.info("Successful registration of user: {}. JWT token: {}", registerDTO.getUsername(), jwtToken);
        return new TokenDTO(jwtToken);
    }

    @Override
    public TokenDTO authUser(AuthDTO authDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword())
            );
        } catch (AuthenticationException ex) {
            throw new UserAuthenticateException(authDTO.getUsername());
        }
        if (userRepository.findByUsername(authDTO.getUsername()) == null) {
            throw new UserNotFoundException(authDTO.getUsername());
        }
        String jwtToken = jwtService.generateToken(authDTO.getUsername());
        log.info("Successful authorize user: {}. JWT token: {}", authDTO.getUsername(), jwtToken);
        return new TokenDTO(jwtToken);
    }

    @Override
    public void setNotificationSetting(String username, NotificationSettingDTO notificationSettingDTO) {
        User user = userRepository.findByUsername(username);
        UserSetting userSetting = userSettingRepository.findByUser(user);
        userSetting = userSetting == null ? new UserSetting() : userSetting;
        if (userSetting.getNotificationSetting() == null) {
            NotificationSetting notificationSetting = NotificationSetting.builder()
                    .pushEnabled(notificationSettingDTO.isPushEnabled())
                    .emailEnabled(notificationSettingDTO.isEmailEnabled())
                    .build();
            notificationUserSettingRepository.save(notificationSetting);
            userSetting.setUser(user);
            userSetting.setNotificationSetting(notificationSetting);
        }
        NotificationSetting userNotificationSetting = userSetting.getNotificationSetting();
        userNotificationSetting.setPushEnabled(notificationSettingDTO.isPushEnabled());
        userNotificationSetting.setEmailEnabled(notificationSettingDTO.isEmailEnabled());
        userSettingRepository.save(userSetting);
    }

    @Override
    public void setUserRoles(String username, List<Role> roles) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }
        user.setRoles(new HashSet<>(roles));
        userRepository.save(user);
    }

    @Override
    public UserDTO blockUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id)
        );
        user.setBlocked(true);
        userRepository.save(user);
        return userFactory.createUserDTO(user);
    }

    @Override
    public List<PushNotificationDTO> getUserPushNotifications(String name) {
        User user = userRepository.findByUsername(name);
        if (user == null) {
            throw new UserNotFoundException(name);
        }

        return remoteNotificationsService.getPushNotifications(user.getId());
    }

}
