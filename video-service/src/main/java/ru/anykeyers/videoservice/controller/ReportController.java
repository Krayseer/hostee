package ru.anykeyers.videoservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.videoservice.domain.report.ReportRequest;
import ru.anykeyers.videoservice.domain.report.ReportDTO;
import ru.anykeyers.videoservice.service.ReportService;

import java.security.Principal;
import java.util.List;

@Tag(name = "Обработка жалоб")
@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "Получить список жалоб")
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<ReportDTO> getReports() {
        return reportService.getAllReports();
    }

    @Operation(summary = "Создать жалобу")
    @PostMapping
    public void createReport(
            @Parameter(description = "Данные о жалобе") @RequestBody ReportRequest reportRequest,
            Principal principal
    ) {
        reportService.createReport(principal.getName(), reportRequest);
    }

    @Operation(summary = "Взять жалобу в обработку")
    @PostMapping("/process/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void processReport(
            @Parameter(description = "Идентификатор жалобы") @PathVariable Long id,
            Principal principal
    ) {
        reportService.processReport(id, principal.getName());
    }

    @Operation(summary = "Обработать жалобу")
    @PostMapping("/finish/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void finishReport(
            @Parameter(description = "Идентификатор жалобы") @PathVariable Long id,
            @Parameter(description = "Решение администратора") @RequestBody String solveResult
    ) {
        reportService.finishReport(id, solveResult);
    }

}
