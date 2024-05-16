package ru.anykeyers.videoservice.service.remote;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.krayseer.domain.dto.PushNotificationDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RemoteNotificationsService {

    private static final String URL = "http://localhost:8082/notification/";

    private final RestTemplate restTemplate;

    public List<PushNotificationDTO> getPushNotifications(Long id) {
        ResponseEntity<List<PushNotificationDTO>> response = restTemplate.exchange(
                URL + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PushNotificationDTO>>() {}
        );
        return response.getBody();
    }
}
