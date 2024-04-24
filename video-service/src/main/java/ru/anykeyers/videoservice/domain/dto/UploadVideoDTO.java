package ru.anykeyers.videoservice.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO с данными для загрузки видео
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadVideoDTO {

    /**
     * Название видео
     */
    @NotBlank(message = "you need to enter video name")
    private String name;

    /**
     * Описание видео
     */
    private String description;

}
