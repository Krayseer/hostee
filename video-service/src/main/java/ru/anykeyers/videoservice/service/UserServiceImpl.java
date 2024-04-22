package ru.anykeyers.videoservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.videoservice.UserRepository;
import ru.anykeyers.videoservice.domain.User;
import ru.anykeyers.videoservice.domain.dto.AuthDTO;
import ru.anykeyers.videoservice.domain.dto.RegisterDTO;
import ru.anykeyers.videoservice.domain.dto.TokenDTO;
import ru.anykeyers.videoservice.UserFactory;
import ru.anykeyers.videoservice.exception.UserAlreadyExistsException;
import ru.anykeyers.videoservice.security.JwtService;

/**
 * Реализация сервиса для работы с пользователями
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    @Override
    public TokenDTO registerUser(RegisterDTO registerDTO) {
        if (userRepository.findByUsername(registerDTO.getUsername()) != null) {
            throw new UserAlreadyExistsException(registerDTO.getUsername());
        }
        User userFromDTO = UserFactory.createUser(registerDTO);
        User user = userRepository.save(userFromDTO);
        return new TokenDTO(jwtService.generateToken(user.getUsername()));
    }

    @Override
    public TokenDTO authUser(AuthDTO authDTO) {
        return new TokenDTO(jwtService.generateToken(authDTO.getUsername()));
    }

}
