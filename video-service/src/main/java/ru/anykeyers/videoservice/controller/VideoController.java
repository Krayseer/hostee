package ru.anykeyers.videoservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.videoservice.domain.History;
import ru.anykeyers.videoservice.domain.video.VideoDTO;
import ru.anykeyers.videoservice.domain.video.VideoRequest;
import ru.anykeyers.videoservice.service.HistoryService;
import ru.anykeyers.videoservice.service.VideoService;

import java.security.Principal;
import java.util.List;

@Tag(name = "Обработка видеороликов")
@RestController
@RequiredArgsConstructor
@RequestMapping("/video")
public class VideoController {

    private final VideoService videoService;

    private final HistoryService historyService;

    @Operation(summary = "Получить видео")
    @GetMapping("/{id}")
    public ResponseEntity<Resource> getVideo(
            @Parameter(description = "Идентификатор видео") @PathVariable("id") Long id,
            Principal principal
    ) {
        Resource videoResource = videoService.getVideo(id);
        if (principal != null) {
            historyService.addHistory(principal.getName(), id);
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType("video/mp4"))
                .body(videoResource);
    }

    @Operation(summary = "Получить список всех видео")
    @GetMapping
    public List<VideoDTO> getAllVideo() {
        return videoService.getAllVideo();
    }

    @Operation(summary = "Получить список видео авторизованного пользователя")
    @GetMapping("/user")
    public List<VideoDTO> getUserVideos(Principal principal) {
        return videoService.getVideos(principal.getName());
    }

    @Operation(summary = "Получить историю просмотра видео авторизованного пользователя")
    @GetMapping("/user/{userId}")
    public List<VideoDTO> getVideosByUserId(@PathVariable Long userId) {
        return videoService.getVideosByUserId(userId);
    }

    @Operation(summary = "Получить историю просмотра видео атворизованного пользователя")
    @GetMapping("/history")
    public History getUserHistory(Principal principal) {
        return historyService.getHistory(principal.getName());
    }

    @Operation(summary = "Загрузить видео")
    @PostMapping
    public void uploadVideo(
            @Parameter(description = "Данные о видео") VideoRequest videoDTO,
            Principal user
    ) {
        videoService.uploadVideo(user.getName(), videoDTO);
    }

    @Operation(summary = "Удалить видео")
    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional
    public void deleteVideo(
            @Parameter(description = "Идентификатор видео") @PathVariable String uuid
    ) {
        videoService.deleteVideo(uuid);
    }

}
