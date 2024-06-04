package ru.anykeyers.storageservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.storageservice.service.VideoStorageServiceCompound;

/**
 * REST контроллер для обработки видео
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/video")
public class VideoStorageController {

    private final VideoStorageServiceCompound storageServiceCompound;

    @GetMapping("/{uuid}")
    public ResponseEntity<Resource> getVideo(@PathVariable String uuid) {
        Resource videoResource = storageServiceCompound.getVideo(uuid);
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType("video/mp4"))
                .body(videoResource);
    }

    @PostMapping
    public String saveVideo(@RequestBody byte[] fileBytes) {
        return storageServiceCompound.saveVideo(fileBytes);
    }

}
