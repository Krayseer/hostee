package ru.anykeyers.storageservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.anykeyers.storageservice.VideoCassandraRepository;
import ru.anykeyers.storageservice.domain.Video;
import ru.anykeyers.storageservice.domain.VideoFile;
import ru.anykeyers.storageservice.service.VideoStorageService;

import java.nio.ByteBuffer;
import java.util.UUID;

@RequiredArgsConstructor
@Service("cassandraVideoStorageService")
public class CassandraVideoStorageService implements VideoStorageService {

    private final VideoCassandraRepository videoCassandraRepository;

    @Override
    public Resource getVideo(String uuid) {
        Video video = videoCassandraRepository.findById(uuid).orElseThrow(() -> new RuntimeException("Video not found"));
        return new ByteArrayResource(video.getByteBuffer().array());
    }

    @Override
    public String saveVideo(byte[] content) {
        VideoFile videoFile = new VideoFile(UUID.randomUUID().toString(), content);
        return saveVideo(videoFile);
    }

    @Override
    public String saveVideo(VideoFile videoFile) {
        Video video = new Video(videoFile.getFileName(), ByteBuffer.wrap(videoFile.getContent()));
        videoCassandraRepository.save(video);
        return video.getUuid();
    }

}

