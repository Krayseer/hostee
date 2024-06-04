package ru.anykeyers.videoservice.controller;

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

/**
 * REST-контроллер для работы с видео
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/video")
public class VideoController {

    private final VideoService videoService;

    private final HistoryService historyService;

    @GetMapping("/{uuid}")
    public ResponseEntity<Resource> getVideo(@PathVariable("uuid") String uuid,
                                             Principal principal) {
        Resource videoResource = videoService.getVideo(uuid);
        if (principal != null) {
            historyService.addHistory(principal.getName(), uuid);
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType("video/mp4"))
                .body(videoResource);
    }

    @GetMapping
    public List<VideoDTO> getAllVideo() {
        return videoService.getAllVideo();
    }

    @GetMapping("/history")
    public History getUserHistory(Principal principal) {
        return historyService.getHistory(principal.getName());
    }

    @PostMapping
    public void uploadVideo(VideoRequest videoDTO, Principal user) {
        videoService.uploadVideo(user.getName(), videoDTO);
    }

    @DeleteMapping("/delete/video/{uuid}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional
    public void deleteVideo(@PathVariable String uuid) {
        videoService.deleteVideo(uuid);
    }

}
