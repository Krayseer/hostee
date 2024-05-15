package ru.anykeyers.storageservice.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.storageservice.service.VideoStorageService;

/**
 * REST контроллер для обработки видео
 */
@RestController
@RequestMapping("/video")
public class VideoStorageController {

    private final VideoStorageService videoStorageService;

    private VideoStorageController(@Qualifier("localVideoStorageService") VideoStorageService videoStorageService) {
        this.videoStorageService = videoStorageService;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Resource> getVideo(@PathVariable String uuid) {
        Resource videoResource = videoStorageService.getVideo(uuid);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("video/mp4")).body(videoResource);
    }

    @PostMapping
    @SneakyThrows
    public String saveVideo(@RequestParam("file") MultipartFile file) {
        return videoStorageService.saveVideo(file.getBytes());
    }

}
