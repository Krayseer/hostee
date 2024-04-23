package ru.anykeyers.storageservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Видео файл
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoFile {

    /**
     * Уникальное название файла
     */
    private String fileName;

    /**
     * Контент видео
     */
    private byte[] content;

}
