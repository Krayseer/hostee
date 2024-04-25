package ru.anykeyers.videoservice.factory;

import org.springframework.stereotype.Component;
import ru.anykeyers.videoservice.domain.Channel;
import ru.anykeyers.videoservice.domain.Video;
import ru.anykeyers.videoservice.domain.dto.UploadVideoDTO;

/**
 * Фабрика для создания видео
 */
@Component
public class VideoFactory {

    /**
     * Создать видео на основе данных из DTO и канала пользователя
     *
     * @param uploadVideoDTO данные для создания видео
     * @param channel канал пользователя
     */
    public Video createVideoFromDto(UploadVideoDTO uploadVideoDTO, Channel channel) {
        return Video.builder()
                .name(uploadVideoDTO.getName())
                .description(uploadVideoDTO.getDescription())
                .channel(channel)
                .build();
    }

}
