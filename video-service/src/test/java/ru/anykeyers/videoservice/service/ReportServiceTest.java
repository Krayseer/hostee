package ru.anykeyers.videoservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.anykeyers.videoservice.domain.report.Report;
import ru.anykeyers.videoservice.domain.report.ReportRequest;
import ru.anykeyers.videoservice.domain.report.ReportState;
import ru.anykeyers.videoservice.domain.user.User;
import ru.anykeyers.videoservice.exception.ReportNotFoundException;
import ru.anykeyers.videoservice.exception.UserNotFoundException;
import ru.anykeyers.videoservice.repository.ReportRepository;
import ru.anykeyers.videoservice.repository.UserRepository;
import ru.anykeyers.videoservice.service.impl.ReportServiceImpl;

import java.util.Optional;

/**
 * Тесты для {@link ReportService}
 */
@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReportRepository reportRepository;

    private final ReportStateService reportStateService = new ReportStateService();

    @InjectMocks
    private ReportServiceImpl reportService;

    @Captor
    private ArgumentCaptor<Report> reportCaptor;

    private final User user = User.builder().username("test-user").build();

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl(userRepository, reportRepository, reportStateService);
    }

    /**
     * Тест создания жалобы от несуществующего пользователя
     */
    @Test
    void createReportFromNotExistingUser() {
        Mockito.when(userRepository.findByUsername("test-user")).thenReturn(null);

        UserNotFoundException exception = Assertions.assertThrows(
                UserNotFoundException.class, () -> reportService.createReport("test-user", Mockito.mock(ReportRequest.class))
        );

        Assertions.assertEquals("User with username 'test-user' not found", exception.getMessage());
    }

    /**
     * Тест создания жалобы на несуществующего пользователя
     */
    @Test
    void createReportToNotExistingUser() {
        ReportRequest reportRequest = new ReportRequest("non-existing", "text");

        Mockito.when(userRepository.findByUsername("test-user")).thenReturn(user);
        Mockito.when(userRepository.findByUsername("non-existing")).thenReturn(null);

        UserNotFoundException exception = Assertions.assertThrows(
                UserNotFoundException.class, () -> reportService.createReport("test-user", reportRequest)
        );

        Assertions.assertEquals("User with username 'non-existing' not found", exception.getMessage());
    }

    /**
     * Тест создания жалобы на пользователя
     */
    @Test
    void createReport() {
        ReportRequest reportRequest = new ReportRequest("second-user", "text");
        User toUser = User.builder().username("second-user").build();
        Mockito.when(userRepository.findByUsername("test-user")).thenReturn(user);
        Mockito.when(userRepository.findByUsername("second-user")).thenReturn(toUser);

        reportService.createReport("test-user", reportRequest);

        Mockito.verify(reportRepository, Mockito.times(1)).save(reportCaptor.capture());

        Report report = reportCaptor.getValue();
        Assertions.assertEquals(user, report.getUserSender());
        Assertions.assertEquals(toUser, report.getUserTarget());
        Assertions.assertEquals("text", report.getText());
        Assertions.assertEquals(ReportState.WAIT_PROCESS, report.getReportState());
    }

    /**
     * Тест обработки несуществующей жалобы
     */
    @Test
    void processNonExistingReport() {
        Long reportId = 1L;
        Mockito.when(reportRepository.findById(reportId)).thenReturn(Optional.empty());

        ReportNotFoundException exception = Assertions.assertThrows(
                ReportNotFoundException.class, () -> reportService.processReport(reportId, "admin")
        );

        Assertions.assertEquals("Report not found with id: 1", exception.getMessage());
    }

    /**
     * Тест принятия жалобы в обработку администратором
     */
    @Test
    void processReport() {
        Report report = Report.builder().id(1L).reportState(ReportState.WAIT_PROCESS).build();

        Mockito.when(reportRepository.findById(1L)).thenReturn(Optional.ofNullable(report));

        reportService.processReport(1L, "admin");

        Mockito.verify(reportRepository, Mockito.times(1)).save(reportCaptor.capture());
        Report captureReport = reportCaptor.getValue();
        Assertions.assertEquals(ReportState.IN_PROCESS, captureReport.getReportState());
        Assertions.assertEquals("admin", captureReport.getSolver());
    }

    /**
     * Тест записи результата обработки несуществующей жалобе
     */
    @Test
    void finishNotExistingReport() {
        processNonExistingReport();
    }

    /**
     * Тест записи результата обработки жалобы
     */
    @Test
    void finishReport() {
        Report report = Report.builder().id(1L).reportState(ReportState.IN_PROCESS).build();

        Mockito.when(reportRepository.findById(1L)).thenReturn(Optional.ofNullable(report));

        reportService.finishReport(1L, "result");

        Mockito.verify(reportRepository, Mockito.times(1)).save(reportCaptor.capture());
        Report captureReport = reportCaptor.getValue();
        Assertions.assertEquals(ReportState.PROCESSED, captureReport.getReportState());
        Assertions.assertEquals("result", captureReport.getResultSolve());
    }

}