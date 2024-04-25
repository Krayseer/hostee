package ru.anykeyers.videoservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.videoservice.ApplicationConfig;
import ru.anykeyers.videoservice.service.RemoteVideoStorageService;

import java.io.File;
import java.io.IOException;

/**
 * Реализация сервиса для загрузки и получения видео из хранилища
 */
@Service
@RequiredArgsConstructor
public class RemoteVideoStorageServiceImpl implements RemoteVideoStorageService {

    private final ApplicationConfig applicationConfig;

    private final RestTemplate restTemplate;

    @Override
    public Resource getVideoFile(String uuid) {
        String url = String.format("%s/video/%s", applicationConfig.getStorageServiceUrl(), uuid);
        ResponseEntity<Resource> response = restTemplate.getForEntity(url, Resource.class);
        return response.getBody();
    }

    @Override
    public ResponseEntity<String> uploadVideoFile(MultipartFile video) {
        String url = String.format("%s/video", applicationConfig.getStorageServiceUrl());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(convert(video)));
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        return restTemplate.postForEntity(url, requestEntity, String.class);
    }

    /**
     * Ковертировать {@link MultipartFile} в {@link File}
     */
    private File convert(MultipartFile file) {
        File convFile = new File(file.getOriginalFilename());
        try {
            file.transferTo(convFile);
        } catch (IOException exception) {
            throw new RuntimeException("Cant convert file");
        }
        return convFile;
    }

}
