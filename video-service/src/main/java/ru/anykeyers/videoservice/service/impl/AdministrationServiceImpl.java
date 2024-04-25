package ru.anykeyers.videoservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.videoservice.domain.User;
import ru.anykeyers.videoservice.exception.UserNotFoundException;
import ru.anykeyers.videoservice.repository.UserRepository;
import ru.anykeyers.videoservice.repository.VideoRepository;
import ru.anykeyers.videoservice.service.AdministrationService;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса администрирования
 */
@Service
@RequiredArgsConstructor
public class AdministrationServiceImpl implements AdministrationService {

    private final UserRepository userRepository;

    private final VideoRepository videoRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User blockUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(id);
        }

        User user = optionalUser.get();
        user.setBlocked(true);
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public void deleteVideo(String uuid) {
        videoRepository.deleteByVideoUuid(uuid);
    }

}
