package ru.anykeyers.videoservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.videoservice.domain.dto.UploadVideoDTO;
import ru.anykeyers.videoservice.service.VideoService;

import java.security.Principal;

/**
 * REST-контроллер для работы с видео
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/video")
public class VideoController {

    private final VideoService videoService;

    /**
     * Получить видео по его id в хранилище
     *
     * @param uuid id видео в сервисе хранилища
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<Resource> getVideo(@PathVariable("uuid") String uuid) {
        Resource videoResource = videoService.getVideo(uuid);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("video/mp4")).body(videoResource);
    }

    /**
     * Загрузить видео
     *
     * @param file видео
     * @param user текуший пользователь
     */
    @PostMapping
    public void uploadVideo(@RequestParam("file") MultipartFile file, Principal user) {
        // TODO: Разобраться, почему нельзя принять MultipartFile и DTO с данными одновременно
        UploadVideoDTO uploadVideoDTO = new UploadVideoDTO("some video", "some descr");
        videoService.uploadVideo(uploadVideoDTO, file, user.getName());
    }

}
