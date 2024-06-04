package ru.anykeyers.videoservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.videoservice.domain.channel.Channel;
import ru.anykeyers.videoservice.domain.video.Video;
import ru.anykeyers.videoservice.exception.ChannelNotExistsException;
import ru.anykeyers.videoservice.repository.ChannelRepository;
import ru.anykeyers.videoservice.repository.VideoRepository;
import ru.krayseer.service.RemoteStatisticsService;
import ru.anykeyers.videoservice.service.StatisticsService;
import ru.krayseer.domain.statistics.ChannelStatisticsDTO;
import ru.krayseer.domain.statistics.VideoStatisticsDTO;

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
    public VideoStatisticsDTO[] getUserVideoStatistics(String username) {
        Channel channel = channelRepository.findChannelByUserUsername(username).orElseThrow(
                () -> new ChannelNotExistsException(username)
        );;
        List<Video> videos = videoRepository.findByChannel(channel);
        return remoteStatisticsService.getVideoStatistics(
                videos.stream().map(Video::getVideoUuid).toArray(String[]::new)
        );
    }

}
