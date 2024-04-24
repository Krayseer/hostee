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
import ru.anykeyers.videoservice.service.RemoteVideoStorageService;

import java.io.File;
import java.io.IOException;

/**
 * Реализация сервиса для загрузки и получения видео из хранилища
 */
@Service
@RequiredArgsConstructor
public class RemoteVideoStorageServiceImpl implements RemoteVideoStorageService {

    /**
     * Endpoint к сервису хранилища
     */
    private final String url = "http://localhost:8081/video";

    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<String> uploadVideoFile(MultipartFile video) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(convert(video)));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        return restTemplate.postForEntity(url, requestEntity, String.class);
    }

    @Override
    public Resource getVideoFile(String uuid) {
        ResponseEntity<Resource> response = restTemplate.getForEntity(url + "/" + uuid, Resource.class);
        return response.getBody();
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
