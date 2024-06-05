package ru.anykeyers.storageservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Чанк видео
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "video_chunk")
public class VideoChunk {

    /**
     * Идентификатор видео
     */
    @PrimaryKeyColumn(name = "video_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID videoId;

    /**
     * Индекс чанка
     */
    @PrimaryKeyColumn(name = "chunk_index", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private int chunkIndex;

    /**
     * Контент чанка в байтах
     */
    private ByteBuffer content;

}
