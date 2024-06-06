package ru.anykeyers.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.anykeyers.statistics.service.StatisticsService;
import ru.krayseer.domain.statistics.ChannelStatisticsDTO;
import ru.krayseer.domain.statistics.VideoStatisticsDTO;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * REST контроллер для сбора статистики
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/channel/{channelId}")
    public ChannelStatisticsDTO getChannelStatistics(@PathVariable Long channelId) {
        return statisticsService.getChannelStatistics(channelId);
    }

    @GetMapping("/channel/{channelId}/pdf")
    public ResponseEntity<byte[]> getPdfStatisticsChannel(@PathVariable Long channelId) {
        ByteArrayOutputStream document = statisticsService.getChannelStatisticsPdf(channelId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=channel_statistics.pdf");
        headers.setContentLength(document.size());
        return new ResponseEntity<>(document.toByteArray(), headers, HttpStatus.OK);
    }

    @GetMapping("/video/{videoId}")
    public VideoStatisticsDTO getVideoStatistics(@PathVariable Long videoId) {
        return statisticsService.getVideoStatistics(videoId);
    }

    @GetMapping("/video/{videoIds}")
    public List<VideoStatisticsDTO> getVideoStatistics(@PathVariable Long[] videoIds) {
        return statisticsService.getVideoStatistics(videoIds);
    }

}
