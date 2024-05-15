package ru.anykeyers.videoservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.anykeyers.videoservice.service.RemoteStatisticsService;
import ru.anykeyers.videoservice.service.StatisticsService;
import ru.krayseer.domain.dto.statistics.ChannelStatisticsDTO;
import ru.krayseer.domain.dto.statistics.VideoStatisticsDTO;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    private final RemoteStatisticsService remoteStatisticsService;

    @GetMapping("/channel")
    public ChannelStatisticsDTO getUserChannelStatistics(Principal principal) {
        return statisticsService.getUserChannelStatistics(principal.getName());
    }

    @GetMapping("/video")
    public VideoStatisticsDTO[] getVideoStatistics(Principal principal) {
        return statisticsService.getUserVideoStatistics(principal.getName());
    }

    @GetMapping("/video/{videoUuid}")
    public VideoStatisticsDTO getVideoStatistics(@PathVariable String videoUuid) {
        return remoteStatisticsService.getVideoStatistics(videoUuid);
    }


}
