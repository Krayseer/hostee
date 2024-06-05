package ru.anykeyers.videoservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.anykeyers.videoservice.domain.report.Report;
import ru.anykeyers.videoservice.domain.report.ReportState;

/**
 * Тесты для {@link ReportStateService}
 */
class ReportStateServiceTest {

    private final ReportStateService reportStateService = new ReportStateService();

    /**
     * Тест назначения состояний жалобе
     */
    @Test
    void nextState() {
        Report report = new Report();
        report.setReportState(reportStateService.nextState(report));
        Assertions.assertEquals(ReportState.WAIT_PROCESS, report.getReportState());
        report.setReportState(reportStateService.nextState(report));
        Assertions.assertEquals(ReportState.IN_PROCESS, report.getReportState());
        report.setReportState(reportStateService.nextState(report));
        Assertions.assertEquals(ReportState.PROCESSED, report.getReportState());
    }

}