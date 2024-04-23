package ru.anykeyers.storageservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.nio.ByteBuffer;

/**
 * Таблица с видео-файлами по идентификатору
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "videos")
public class Video {

    /**
     * Идентификатор
     */
    @Id
    @PrimaryKey
    private String uuid;

    /**
     * Видео-файл в виде BLOB
     */
    private ByteBuffer byteBuffer;

}