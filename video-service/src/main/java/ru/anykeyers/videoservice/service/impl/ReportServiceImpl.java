package ru.anykeyers.videoservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.videoservice.domain.report.Report;
import ru.anykeyers.videoservice.domain.report.ReportRequest;
import ru.anykeyers.videoservice.domain.report.ReportDTO;
import ru.anykeyers.videoservice.domain.user.User;
import ru.anykeyers.videoservice.exception.ReportNotFoundException;
import ru.anykeyers.videoservice.exception.UserNotFoundException;
import ru.anykeyers.videoservice.domain.report.ReportMapper;
import ru.anykeyers.videoservice.repository.ReportRepository;
import ru.anykeyers.videoservice.repository.UserRepository;
import ru.anykeyers.videoservice.service.ReportService;
import ru.anykeyers.videoservice.service.ReportStateService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final UserRepository userRepository;

    private final ReportRepository reportRepository;

    private final ReportStateService reportStateService;

    @Override
    public List<ReportDTO> getAllReports() {
        List<Report> reports = reportRepository.findAll();
        return reports.stream().map(ReportMapper::createDTO).collect(Collectors.toList());
    }

    @Override
    public void createReport(String username, ReportRequest reportRequest) {
        User userSender = userRepository.findByUsername(username);
        if (userSender == null) {
            throw new UserNotFoundException(username);
        }
        User userTarget = userRepository.findByUsername(reportRequest.getUser());
        if (userTarget == null) {
            throw new UserNotFoundException(reportRequest.getUser());
        }
        Report report = ReportMapper.createReport(userSender, userTarget, reportRequest);
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
