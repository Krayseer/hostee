package ru.anykeyers.storageservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.anykeyers.storageservice.VideoCassandraRepository;
import ru.anykeyers.storageservice.domain.Video;
import ru.anykeyers.storageservice.domain.VideoFile;
import ru.anykeyers.storageservice.exception.VideoNotFoundException;
import ru.anykeyers.storageservice.service.VideoStorageService;

import java.nio.ByteBuffer;

/**
 * Реализация сервиса обработки видео в Cassandra
 */
@Slf4j
@RequiredArgsConstructor
@Service("cassandraVideoStorageService")
public class CassandraVideoStorageService implements VideoStorageService {

    private final VideoCassandraRepository videoCassandraRepository;

    @Override
    public Resource getVideo(String uuid) {
        Video video = videoCassandraRepository.findById(uuid).orElseThrow(() -> new VideoNotFoundException(uuid));
        return new ByteArrayResource(video.getByteBuffer().array());
    }

    @Override
    public String saveVideo(VideoFile videoFile) {
        log.info("Saving video: {}", videoFile.getFileName());
        Video video = new Video(videoFile.getFileName(), ByteBuffer.wrap(videoFile.getContent()));
        videoCassandraRepository.save(video);
        return video.getUuid();
    }

}

