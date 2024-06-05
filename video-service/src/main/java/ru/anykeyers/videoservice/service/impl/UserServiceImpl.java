package ru.anykeyers.videoservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.anykeyers.videoservice.domain.user.Role;
import ru.anykeyers.videoservice.domain.user.UserSetting;
import ru.krayseer.domain.UserSettingDTO;
import ru.krayseer.domain.UserDTO;
import ru.anykeyers.videoservice.exception.UserAuthenticateException;
import ru.anykeyers.videoservice.repository.UserRepository;
import ru.anykeyers.videoservice.domain.user.User;
import ru.anykeyers.videoservice.domain.dto.AuthDTO;
import ru.anykeyers.videoservice.domain.user.RegisterDTO;
import ru.anykeyers.videoservice.domain.dto.TokenDTO;
import ru.anykeyers.videoservice.domain.user.UserMapper;
import ru.anykeyers.videoservice.exception.UserAlreadyExistsException;
import ru.anykeyers.videoservice.exception.UserNotFoundException;
import ru.anykeyers.videoservice.security.JwtService;
import ru.anykeyers.videoservice.service.UserService;

import java.util.HashSet;
import java.util.List;

/**
 * Реализация сервиса для работы с пользователями
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Override
    public UserDTO getUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }
        return UserMapper.createDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::createDTO).toList();
    }

    @Override
    public TokenDTO registerUser(RegisterDTO registerDTO) {
        if (userRepository.findByUsername(registerDTO.getUsername()) != null) {
            throw new UserAlreadyExistsException(registerDTO.getUsername());
        }
        User userFromDTO = UserMapper.createUser(registerDTO, passwordEncoder);
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
    public void setNotificationSetting(String username, UserSettingDTO userSettingDTO) {
        User user = userRepository.findByUsername(username);
        UserSetting userSetting = user.getUserSetting();
        userSetting.setPushEnabled(userSettingDTO.isPushEnabled());
        userSetting.setEmailEnabled(userSettingDTO.isEmailEnabled());
        user.setUserSetting(userSetting);
        userRepository.save(user);
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
        return UserMapper.createDTO(user);
    }

    @Override
    public UserDTO unblockUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id)
        );
        user.setBlocked(false);
        userRepository.save(user);
        return UserMapper.createDTO(user);
    }

}
