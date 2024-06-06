package ru.anykeyers.videoservice.domain.video;

import ru.anykeyers.videoservice.domain.channel.Channel;
import ru.krayseer.domain.statistics.VideoStatisticsDTO;

import java.time.Instant;

/**
 * Фабрика для создания видео
 */
public final class VideoMapper {

    /**
     * Создать видео на основе данных из DTO и канала пользователя
     *
     * @param videoRequest  данные для создания видео
     * @param channel       канал пользователя
     */
    public static Video createVideo(VideoRequest videoRequest, Channel channel) {
        return Video.builder()
                .name(videoRequest.getName())
                .description(videoRequest.getDescription())
                .uploadStatus(UploadStatus.PROCESSING)
                .channel(channel)
                .createdAt(Instant.now())
                .build();
    }

    /**
     * Создать DTO с данными о видео
     *
     * @param video видео
     */
    public static VideoDTO createDTO(Video video) {
        return VideoDTO.builder()
                .id(video.getId())
                .uuid(video.getVideoUuid())
                .name(video.getName())
                .description(video.getDescription())
                .createdAt(video.getCreatedAt().toString())
                .build();
    }

    /**
     * Создать DTO с данными о видео со статистикой
     *
     * @param video             видео
     * @param videoStatistic    статистика
     */
    public static VideoDTO createDTO(Video video, VideoStatisticsDTO videoStatistic) {
        return VideoDTO.builder()
                .id(video.getId())
                .uuid(video.getVideoUuid())
                .name(video.getName())
                .description(video.getDescription())
                .createdAt(video.getCreatedAt().toString())
                .statistics(videoStatistic)
                .channel(video.getChannel())
                .build();
    }

}
