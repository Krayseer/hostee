package ru.anykeyers.videoservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.videoservice.domain.report.ReportRequest;
import ru.anykeyers.videoservice.domain.report.ReportDTO;
import ru.anykeyers.videoservice.service.ReportService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<ReportDTO> getReports() {
        return reportService.getAllReports();
    }

    @PostMapping
    public void createReport(@RequestBody ReportRequest reportRequest, Principal principal) {
        reportService.createReport(principal.getName(), reportRequest);
    }

    @PostMapping("/process/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void processReport(@PathVariable Long id, Principal principal) {
        reportService.processReport(id, principal.getName());
    }

    @PostMapping("/finish/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void finishReport(@PathVariable Long id, @RequestBody String solveResult) {
        reportService.finishReport(id, solveResult);
    }

}
