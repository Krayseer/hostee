package ru.anykeyers.videoservice.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.videoservice.domain.History;
import ru.anykeyers.videoservice.domain.dto.UploadVideoDTO;
import ru.anykeyers.videoservice.service.HistoryService;
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

    private final HistoryService historyService;

    /**
     * Получить видео по его id в хранилище
     *
     * @param uuid id видео в сервисе хранилища
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<Resource> getVideo(@PathVariable("uuid") String uuid,
                                             Principal principal) {
        Resource videoResource = videoService.getVideo(uuid);
        if (principal != null) {
            historyService.addHistory(principal.getName(), uuid);
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("video/mp4")).body(videoResource);
    }

    /**
     * Получить историю просмотренных видео пользователя
     *
     * @param principal данные об атворизованном пользователе
     */
    @GetMapping("/history")
    public History getUserHistory(Principal principal) {
        return historyService.getHistory(principal.getName());
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

    @DeleteMapping("/delete/video/{uuid}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional
    public void deleteVideo(@PathVariable String uuid) {
        videoService.deleteVideo(uuid);
    }

}
