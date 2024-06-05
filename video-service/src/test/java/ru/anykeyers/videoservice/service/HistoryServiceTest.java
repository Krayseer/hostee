package ru.anykeyers.videoservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.anykeyers.videoservice.domain.History;
import ru.anykeyers.videoservice.domain.user.User;
import ru.anykeyers.videoservice.domain.video.Video;
import ru.anykeyers.videoservice.repository.HistoryRepository;
import ru.anykeyers.videoservice.repository.UserRepository;
import ru.anykeyers.videoservice.repository.VideoRepository;
import ru.anykeyers.videoservice.service.impl.HistoryServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Тесты для {@link HistoryService}
 */
@ExtendWith(MockitoExtension.class)
class HistoryServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private HistoryRepository historyRepository;

    @InjectMocks
    private HistoryServiceImpl historyService;

    @Captor
    private ArgumentCaptor<History> historyCaptor;

    private final User user = User.builder().username("test-user").build();

    /**
     * Тест добавления видео в еще не созданную историю пользователя
     */
    @Test
    void addVideoInNewHistory() {
        String videoUuid = "uuid";
        Video video = Video.builder().id(1L).videoUuid(videoUuid).build();

        Mockito.when(userRepository.findByUsername("test-user")).thenReturn(user);
        Mockito.when(historyRepository.findByUser(user)).thenReturn(null);
        Mockito.when(videoRepository.findById(1L)).thenReturn(Optional.of(video));

        historyService.addHistory("test-user", 1L);

        Mockito.verify(historyRepository, Mockito.times(1)).save(historyCaptor.capture());
        History history = historyCaptor.getValue();
        Assertions.assertEquals(user, history.getUser());
        Assertions.assertEquals(1, history.getVideos().size());
        Assertions.assertEquals(videoUuid, history.getVideos().getFirst().getVideoUuid());
    }

    /**
     * Тест добавления видео в существующую историю пользователя
     */
    @Test
    void addVideoInExistingHistory() {
        String videoUuid = "uuid";
        Video video = Video.builder().id(1L).videoUuid(videoUuid).build();
        List<Video> existingVideos = new ArrayList<>();
        existingVideos.add(new Video());
        History existingHistory = History.builder().user(user).videos(existingVideos).build();

        Mockito.when(userRepository.findByUsername("test-user")).thenReturn(user);
        Mockito.when(historyRepository.findByUser(user)).thenReturn(existingHistory);
        Mockito.when(videoRepository.findById(1L)).thenReturn(Optional.of(video));

        historyService.addHistory("test-user", 1L);

        Mockito.verify(historyRepository, Mockito.times(1)).save(historyCaptor.capture());
        History history = historyCaptor.getValue();
        Assertions.assertEquals(user, history.getUser());
        Assertions.assertEquals(2, history.getVideos().size());
        Assertions.assertEquals(videoUuid, history.getVideos().getLast().getVideoUuid());
    }

}