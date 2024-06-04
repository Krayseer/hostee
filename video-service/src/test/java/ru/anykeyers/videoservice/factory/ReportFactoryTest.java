//package ru.anykeyers.videoservice.factory;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import ru.anykeyers.videoservice.domain.report.ReportMapper;
//import ru.anykeyers.videoservice.domain.user.User;
//import ru.anykeyers.videoservice.domain.report.Report;
//import ru.anykeyers.videoservice.domain.report.ReportRequest;
//import ru.anykeyers.videoservice.exception.UserNotFoundException;
//import ru.anykeyers.videoservice.repository.UserRepository;
//
///**
// * Тесты для фабрики создания жалоб
// */
//@ExtendWith(MockitoExtension.class)
//class ReportFactoryTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private ReportMapper reportMapper;
//
//    /**
//     * Тест создания жалобы из DTO
//     */
//    @Test
//    void createReport() {
//        String sender = "sender-name";
//        ReportRequest reportRequest = new ReportRequest("test-user", "test-report-text");
//        User senderUser = User.builder().username("sender-name").build();
//        User targetUser = User.builder().username("test-user").build();
//
//        Mockito.when(userRepository.findByUsername("sender-name")).thenReturn(senderUser);
//        Mockito.when(userRepository.findByUsername("test-user")).thenReturn(targetUser);
//        Report report = reportMapper.createReport(sender, reportRequest);
//
//        Assertions.assertEquals("sender-name", report.getUserSender().getUsername());
//        Assertions.assertEquals("test-user", report.getUserTarget().getUsername());
//        Assertions.assertEquals("test-report-text", report.getText());
//    }
//
//    /**
//     * Тест создания жалобы из DTO с несуществующим именем пользователя отправителя жалобы
//     */
//    @Test
//    void createReportWithUnknownSender() {
//        ReportRequest reportRequest = new ReportRequest("test-user", "test-report-text");
//        Mockito.when(userRepository.findByUsername("sender-name")).thenReturn(null);
//        UserNotFoundException userNotFoundException = Assertions.assertThrows(
//                UserNotFoundException.class, () -> reportMapper.createReport("sender-name", reportRequest)
//        );
//        Assertions.assertEquals(userNotFoundException.getMessage(), "User with username 'sender-name' not found");
//    }
//
//    /**
//     * Тест создания жалобы из DTO с несуществующим именем пользователя, на которого направлена жалоба
//     */
//    @Test
//    void createReportWithUnknownTarget() {
//        ReportRequest reportRequest = new ReportRequest("test-user", "test-report-text");
//        Mockito.when(userRepository.findByUsername("sender-name")).thenReturn(new User());
//        Mockito.when(userRepository.findByUsername("test-user")).thenReturn(null);
//        UserNotFoundException userNotFoundException = Assertions.assertThrows(
//                UserNotFoundException.class, () -> reportMapper.createReport("sender-name", reportRequest)
//        );
//        Assertions.assertEquals(userNotFoundException.getMessage(), "User with username 'test-user' not found");
//    }
//
//}