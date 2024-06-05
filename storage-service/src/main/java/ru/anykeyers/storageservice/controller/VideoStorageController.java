package ru.anykeyers.storageservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.storageservice.service.VideoStorageServiceCompound;

@Tag(name = "Обработка видеороликов")
@RestController
@RequiredArgsConstructor
@RequestMapping("/video")
public class VideoStorageController {

    private final VideoStorageServiceCompound storageServiceCompound;

    @Operation(summary = "Получить видеоролик")
    @GetMapping("/{uuid}")
    public ResponseEntity<Resource> getVideo(
            @Parameter(description = "Идентификатор видеоролика") @PathVariable String uuid
    ) {
        Resource videoResource = storageServiceCompound.getVideo(uuid);
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType("video/mp4"))
                .body(videoResource);
    }

    @Operation(summary = "Сохранить видеоролик")
    @PostMapping
    public String saveVideo(
            @Parameter(description = "Видеоролик с байтовом представлении") @RequestBody byte[] fileBytes
    ) {
        return storageServiceCompound.saveVideo(fileBytes);
    }

}
