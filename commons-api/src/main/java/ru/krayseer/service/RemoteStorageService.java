package ru.krayseer.service;

import lombok.SneakyThrows;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ru.krayseer.RemoteConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Удаленный сервис хранилища
 */
public class RemoteStorageService {

    private final String URL;

    private final RestTemplate restTemplate;

    public RemoteStorageService(RestTemplate restTemplate,
                                RemoteConfiguration remoteConfiguration) {
        this.restTemplate = restTemplate;
        this.URL = remoteConfiguration.getStorageServiceUrl();
    }

    /**
     * Получить видео из хранилища
     *
     * @param uuid идентификатор видео
     */
    public Resource getVideoFile(String uuid) {
        ResponseEntity<Resource> response = restTemplate.getForEntity(URL + "/video/" + uuid, Resource.class);
        return response.getBody();
    }

    /**
     * Загрузить видео в хранилище
     *
     * @param video видео
     */
    @SneakyThrows
    public ResponseEntity<String> uploadVideoFile(MultipartFile video) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return restTemplate.postForEntity(URL + "/video", new HttpEntity<>(video.getBytes(), headers), String.class);
    }

    @SneakyThrows
    public ResponseEntity<String> uploadPhoto(MultipartFile photo) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<byte[]> requestEntity = new HttpEntity<>(photo.getBytes(), headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    URL + "/photo", HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {}
            );
            if (response.getStatusCode().is2xxSuccessful()) {
                return response;
            }
            ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());
            if (response.hasBody()) {
                return responseBuilder.body(response.getBody());
            }
            return responseBuilder.build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }

}
