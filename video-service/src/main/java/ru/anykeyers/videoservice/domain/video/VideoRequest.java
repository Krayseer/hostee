package ru.anykeyers.videoservice.domain.video;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * DTO с данными для загрузки видео
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoRequest {

    /**
     * Название видео
     */
    @NotBlank(message = "you need to enter video name")
    private String name;

    /**
     * Описание видео
     */
    private String description;

    /**
     * Видеоролик
     */
    private MultipartFile video;

    /**
     * Превью видео
     */
    private MultipartFile preview;

    public VideoRequest(String name, String description, MultipartFile video) {
        this.name = name;
        this.description = description;
        this.video = video;
    }

}
