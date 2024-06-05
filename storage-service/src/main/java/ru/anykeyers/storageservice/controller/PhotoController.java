package ru.anykeyers.storageservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.storageservice.service.PhotoService;

@Tag(name = "Обработка фотографий")
@RestController
@RequiredArgsConstructor
@RequestMapping("/photo")
public class PhotoController {

    private final PhotoService photoService;

    @Operation(summary = "Загрузить фотографию")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String upload(
            @Parameter(description = "Фотография в байтовом представлении") @RequestBody byte[] photoBytes
    ) {
        return photoService.uploadPhoto(photoBytes);
    }

}
