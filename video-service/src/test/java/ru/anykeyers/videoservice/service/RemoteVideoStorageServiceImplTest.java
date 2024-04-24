package ru.anykeyers.videoservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.videoservice.service.impl.RemoteVideoStorageServiceImpl;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.*;

/**
 * Тесты для сервис {@link RemoteVideoStorageServiceImpl}
 */
@ExtendWith(MockitoExtension.class)
public class RemoteVideoStorageServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RemoteVideoStorageServiceImpl remoteVideoStorageServiceImpl;

    /**
     * Тест загрузки видео в хранилище
     */
    @Test
    void uploadVideoFileTest() throws IOException {
        MultipartFile mockMultipartFile = mock(MultipartFile.class);
        when(mockMultipartFile.getOriginalFilename()).thenReturn("test_video.mp4");
        File mockFile = File.createTempFile("test_video", ".mp4");

        ResponseEntity<String> mockResponseEntity = ResponseEntity.ok("video_uuid");
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(String.class)))
                .thenReturn(mockResponseEntity);

        ResponseEntity<String> result = remoteVideoStorageServiceImpl.uploadVideoFile(mockMultipartFile);

        verify(restTemplate, times(1)).postForEntity(anyString(), any(HttpEntity.class), eq(String.class));
        Assertions.assertEquals(mockResponseEntity, result);
    }

    /**
     * Тест получения видео из хранилища
     */
    @Test
    public void testGetVideoFile() {
        ResponseEntity<Resource> mockResponseEntity = ResponseEntity.ok(new FileSystemResource("test_video.mp4"));
        when(restTemplate.getForEntity(anyString(), eq(Resource.class)))
                .thenReturn(mockResponseEntity);

        Resource result = remoteVideoStorageServiceImpl.getVideoFile("video_uuid");

        verify(restTemplate, times(1)).getForEntity(anyString(), eq(Resource.class));
        Assertions.assertEquals(mockResponseEntity.getBody(), result);
    }

}
