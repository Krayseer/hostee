package ru.anykeyers.storageservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import ru.anykeyers.storageservice.VideoChunkRepository;
import ru.anykeyers.storageservice.domain.VideoChunk;
import ru.anykeyers.storageservice.domain.VideoFile;
import ru.anykeyers.storageservice.service.VideoStorageService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Реализация сервиса обработки видео в Cassandra
 */
@Slf4j
@RequiredArgsConstructor
@Service("cassandraVideoStorageService")
public class CassandraVideoStorageService implements VideoStorageService {

    private final VideoChunkRepository videoChunkRepository;

    @Override
    public Resource getVideo(String uuid) {
        List<VideoChunk> videoChunks = getVideoChunks(uuid);
        return new ByteArrayResource(getVideo(videoChunks).array());
    }

    @Override
    public String saveVideo(VideoFile videoFile) {
        UUID videoId = UUID.fromString(videoFile.getFileName());
        InputStream inputStream = new ByteArrayInputStream(videoFile.getContent());
        int chunkSize = 1024 * 1024;
        byte[] buffer = new byte[chunkSize];
        int bytesRead, chunkIndex = 0;
        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                ByteBuffer chunkData = ByteBuffer.wrap(Arrays.copyOf(buffer, bytesRead));
                VideoChunk chunk = new VideoChunk(videoId, chunkIndex++, chunkData);
                videoChunkRepository.save(chunk);
            }
        } catch (IOException e) {
            log.error("Error while reading video content", e);
            throw new RuntimeException("Failed to save video", e);
        }
        log.info("Saving video in cassandra: {}", videoFile.getFileName());
        return videoId.toString();
    }

    private List<VideoChunk> getVideoChunks(String uuid) {
        List<VideoChunk> allChunks = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 20);
        int chunkIndex = 0;
        boolean hasMoreChunks;
        do {
            Slice<VideoChunk> chunkPage = videoChunkRepository.findByVideoIdAndChunkIndexGreaterThanEqualOrderByChunkIndexAsc(UUID.fromString(uuid), chunkIndex, pageable);
            List<VideoChunk> chunks = chunkPage.getContent();
            allChunks.addAll(chunks);
            hasMoreChunks = chunkPage.hasNext();
            if (!chunks.isEmpty()) {
                chunkIndex = chunks.getLast().getChunkIndex() + 1;
            }
        } while (hasMoreChunks);
        return allChunks;
    }

    private ByteBuffer getVideo(List<VideoChunk> chunks) {
        int totalSize = chunks.stream().mapToInt(chunk -> chunk.getContent().remaining()).sum();
        ByteBuffer byteBuffer = ByteBuffer.allocate(totalSize);
        for (VideoChunk chunk : chunks) {
            byteBuffer.put(chunk.getContent());
        }
        return byteBuffer;
    }

}

