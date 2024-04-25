package ru.anykeyers.videoservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.videoservice.domain.report.Report;
import ru.anykeyers.videoservice.domain.report.ReportDTO;
import ru.anykeyers.videoservice.domain.report.ReportResponse;
import ru.anykeyers.videoservice.exception.ReportNotFoundException;
import ru.anykeyers.videoservice.factory.ReportFactory;
import ru.anykeyers.videoservice.repository.ReportRepository;
import ru.anykeyers.videoservice.service.ReportService;
import ru.anykeyers.videoservice.service.ReportStateService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    private final ReportFactory reportFactory;

    private final ReportStateService reportStateService;

    @Override
    public List<ReportResponse> getAllReports() {
        List<Report> reports = reportRepository.findAll();
        return reports.stream().map(reportFactory::createResponse).collect(Collectors.toList());
    }

    @Override
    public void createReport(String username, ReportDTO reportDTO) {
        Report report = reportFactory.createReport(username, reportDTO);
        report.setReportState(reportStateService.nextState(report));
        reportRepository.save(report);
    }

    @Override
    public void processReport(Long reportId, String username) {
        Report report = reportRepository.findById(reportId).orElseThrow(
                () -> new ReportNotFoundException(reportId)
        );
        report.setReportState(reportStateService.nextState(report));
        report.setSolver(username);
        reportRepository.save(report);
    }

    @Override
    public void finishReport(Long reportId, String resultSolve) {
        Report report = reportRepository.findById(reportId).orElseThrow(
                () -> new ReportNotFoundException(reportId)
        );
        report.setReportState(reportStateService.nextState(report));
        report.setResultSolve(resultSolve);
        reportRepository.save(report);
    }

}
