package ru.anykeyers.videoservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import ru.anykeyers.videoservice.UserRepository;
import ru.anykeyers.videoservice.domain.User;
import ru.anykeyers.videoservice.domain.dto.AuthDTO;
import ru.anykeyers.videoservice.domain.dto.RegisterDTO;
import ru.anykeyers.videoservice.domain.dto.TokenDTO;
import ru.anykeyers.videoservice.UserFactory;
import ru.anykeyers.videoservice.exception.UserAlreadyExistsException;
import ru.anykeyers.videoservice.exception.UserNotFoundException;
import ru.anykeyers.videoservice.security.JwtService;

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

    private final AuthenticationManager authenticationManager;

    @Override
    public TokenDTO registerUser(RegisterDTO registerDTO) {
        if (userRepository.findByUsername(registerDTO.getUsername()) != null) {
            throw new UserAlreadyExistsException(registerDTO.getUsername());
        }
        User userFromDTO = userFactory.createUser(registerDTO);
        User user = userRepository.save(userFromDTO);
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
            throw new RuntimeException("Authentication failed");
        }
        if (userRepository.findByUsername(authDTO.getUsername()) == null) {
            throw new UserNotFoundException(authDTO.getUsername());
        }
        String jwtToken = jwtService.generateToken(authDTO.getUsername());
        log.info("Successful authorize user: {}. JWT token: {}", authDTO.getUsername(), jwtToken);
        return new TokenDTO(jwtToken);
    }

}
