package ru.anykeyers.videoservice.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.anykeyers.videoservice.domain.report.Report;
import ru.anykeyers.videoservice.domain.User;
import ru.anykeyers.videoservice.domain.report.ReportDTO;
import ru.anykeyers.videoservice.domain.report.ReportResponse;
import ru.anykeyers.videoservice.exception.UserNotFoundException;
import ru.anykeyers.videoservice.repository.UserRepository;

/**
 * Фабрика по созданию жалоб
 */
@Component
@RequiredArgsConstructor
public class ReportFactory {

    private final UserRepository userRepository;

    private final UserFactory userFactory;

    /**
     * Создать сущность жалобы из данных в DTO
     *
     * @param reportDTO данные для создания жалобы
     */
    public Report createReport(String username, ReportDTO reportDTO) {
        User userSender = userRepository.findByUsername(username);
        if (userSender == null) {
            throw new UserNotFoundException(username);
        }
        User userTarget = userRepository.findByUsername(reportDTO.getUser());
        if (userTarget == null) {
            throw new UserNotFoundException(reportDTO.getUser());
        }
        return Report.builder()
                .userSender(userSender)
                .userTarget(userTarget)
                .text(reportDTO.getText())
                .build();
    }

    /**
     * Создать данные для передачи данных, основанных на жалобе
     *
     * @param report жалоба
     */
    public ReportResponse createResponse(Report report) {
        return new ReportResponse(
                report.getId(),
                userFactory.createUserDTO(report.getUserTarget()),
                userFactory.createUserDTO(report.getUserSender()),
                report.getReportState(),
                report.getText(),
                report.getSolver(),
                report.getResultSolve()
        );
    }

}
