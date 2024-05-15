package ru.anykeyers.videoservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.videoservice.domain.History;
import ru.anykeyers.videoservice.domain.User;
import ru.anykeyers.videoservice.repository.HistoryRepository;
import ru.anykeyers.videoservice.repository.UserRepository;
import ru.anykeyers.videoservice.repository.VideoRepository;
import ru.anykeyers.videoservice.service.HistoryService;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;

    private final UserRepository userRepository;

    private final VideoRepository videoRepository;

    @Override
    public History getHistory(String username) {
        return historyRepository.findByUserUsername(username);
    }

    @Override
    public void addHistory(String username, String videoUuid) {
        User user = userRepository.findByUsername(username);
        History history = historyRepository.findByUser(user);
        if (history == null) {
            history = new History();
            history.setUser(user);
        }
        history.addVideo(videoRepository.findByVideoUuid(videoUuid));
        historyRepository.save(history);
    }

}
