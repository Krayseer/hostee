package ru.anykeyers.videoservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.videoservice.domain.channel.Channel;
import ru.anykeyers.videoservice.domain.video.Video;
import ru.anykeyers.videoservice.domain.video.VideoDTO;
import ru.anykeyers.videoservice.domain.video.VideoMapper;
import ru.anykeyers.videoservice.exception.ChannelNotExistsException;
import ru.anykeyers.videoservice.exception.VideoNotFoundException;
import ru.anykeyers.videoservice.repository.ChannelRepository;
import ru.anykeyers.videoservice.repository.VideoRepository;
import ru.krayseer.service.RemoteStatisticsService;
import ru.anykeyers.videoservice.service.StatisticsService;
import ru.krayseer.domain.statistics.ChannelStatisticsDTO;
import ru.krayseer.domain.statistics.VideoStatisticsDTO;

import java.util.Arrays;
import java.util.List;

/**
 * Реализация сервиса сбора статистики
 */
@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final ChannelRepository channelRepository;

    private final VideoRepository videoRepository;

    private final RemoteStatisticsService remoteStatisticsService;

    @Override
    public ChannelStatisticsDTO getUserChannelStatistics(String username) {
        Channel channel = channelRepository.findChannelByUserUsername(username).orElseThrow(
                () -> new ChannelNotExistsException(username)
        );
        return remoteStatisticsService.getChannelStatistics(channel.getId());
    }

    @Override
    public List<VideoDTO> getUserVideoStatistics(String username) {
        Channel channel = channelRepository.findChannelByUserUsername(username).orElseThrow(
                () -> new ChannelNotExistsException(username)
        );
        List<Video> videos = videoRepository.findByChannel(channel);
        VideoStatisticsDTO[] videoStatistics = remoteStatisticsService.getVideoStatistics(
                videos.stream().map(Video::getId).toArray(Long[]::new)
        );
        return Arrays.stream(videoStatistics)
                .map(videoStatistic -> {
                    Video video = videoRepository.findById(videoStatistic.getVideoId()).orElseThrow(
                            () -> new VideoNotFoundException(videoStatistic.getVideoId())
                    );
                    return VideoMapper.createDTO(video, videoStatistic);
                })
                .toList();
    }

}
