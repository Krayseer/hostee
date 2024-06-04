package ru.anykeyers.videoservice.domain.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.anykeyers.videoservice.domain.user.User;
import ru.anykeyers.videoservice.domain.user.UserMapper;
import ru.anykeyers.videoservice.repository.UserRepository;

/**
 * Фабрика по созданию жалоб
 */
@Component
@RequiredArgsConstructor
public class ReportMapper {

    private final UserRepository userRepository;

    /**
     * Создать сущность жалобы из данных в DTO
     *
     * @param reportRequest данные для создания жалобы
     */
    public static Report createReport(User userSender, User userTarget, ReportRequest reportRequest) {
        return Report.builder()
                .userSender(userSender)
                .userTarget(userTarget)
                .text(reportRequest.getText())
                .build();
    }

    /**
     * Создать данные для передачи данных, основанных на жалобе
     *
     * @param report жалоба
     */
    public static ReportDTO createDTO(Report report) {
        return new ReportDTO(
                report.getId(),
                UserMapper.createDTO(report.getUserTarget()),
                UserMapper.createDTO(report.getUserSender()),
                report.getReportState(),
                report.getText(),
                report.getSolver(),
                report.getResultSolve()
        );
    }

}
