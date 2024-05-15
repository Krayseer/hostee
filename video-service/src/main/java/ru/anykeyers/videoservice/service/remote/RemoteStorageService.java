package ru.anykeyers.videoservice.service.remote;

import lombok.SneakyThrows;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.videoservice.ApplicationConfig;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Удаленный сервис хранилища
 */
@Service
public class RemoteStorageService {

    private final String URL;

    private final RestTemplate restTemplate;

    public RemoteStorageService(ApplicationConfig applicationConfig,
                                RestTemplate restTemplate) {
        this.URL = applicationConfig.getStorageServiceUrl();
        this.restTemplate = restTemplate;
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
    public ResponseEntity<String> uploadVideoFile(MultipartFile video) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(convert(video)));
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        return restTemplate.postForEntity(URL + "/video", requestEntity, String.class);
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

    /**
     * Ковертировать {@link MultipartFile} в {@link File}
     */
    private File convert(MultipartFile file) {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            file.transferTo(convFile);
        } catch (IOException exception) {
            throw new RuntimeException("Cant convert file");
        }
        return convFile;
    }

}
