package ru.anykeyers.videoservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.krayseer.service.RemoteStatisticsService;
import ru.anykeyers.videoservice.service.StatisticsService;
import ru.krayseer.domain.statistics.ChannelStatisticsDTO;
import ru.krayseer.domain.statistics.VideoStatisticsDTO;

import java.security.Principal;

@Tag(name = "Статистика")
@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    private final RemoteStatisticsService remoteStatisticsService;

    @Operation(summary = "Получить статистику канала пользователя")
    @GetMapping("/channel")
    public ChannelStatisticsDTO getUserChannelStatistics(Principal principal) {
        return statisticsService.getUserChannelStatistics(principal.getName());
    }

    @Operation(summary = "Получить статистику по видеороликам")
    @GetMapping("/video")
    public VideoStatisticsDTO[] getVideoStatistics(Principal principal) {
        return statisticsService.getUserVideoStatistics(principal.getName());
    }

    @Operation(summary = "Получить статистику видеоролика")
    @GetMapping("/video/{videoUuid}")
    public VideoStatisticsDTO getVideoStatistics(
            @Parameter(description = "Идентификатор видео") @PathVariable String videoUuid
    ) {
        return remoteStatisticsService.getVideoStatistics(videoUuid);
    }


}
