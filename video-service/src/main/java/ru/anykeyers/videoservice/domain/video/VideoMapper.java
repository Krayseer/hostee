package ru.anykeyers.videoservice.domain.video;

import ru.anykeyers.videoservice.domain.channel.Channel;

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
                .channel(channel)
                .build();
    }

    /**
     * Создать DTO с данными о видео
     *
     * @param video видео
     */
    public static VideoDTO createDTO(Video video) {
        return VideoDTO.builder()
                .uuid(video.getVideoUuid())
                .name(video.getName())
                .description(video.getDescription())
                .build();
    }

}
