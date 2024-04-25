package ru.anykeyers.videoservice.service;

import org.springframework.stereotype.Service;
import ru.anykeyers.videoservice.domain.report.Report;
import ru.anykeyers.videoservice.domain.report.ReportState;

/**
 * Сервис обработки состояний
 */
@Service
public class ReportStateService {

    /**
     * Получить следующее состояние обработки жалобы
     *
     * @param report жалоба
     */
     public ReportState nextState(Report report) {
         switch (report.getReportState()) {
             case null -> {
                 return ReportState.WAIT_PROCESS;
             }
             case WAIT_PROCESS, PROCESSED -> {
                 return ReportState.IN_PROCESS;
             }
             case IN_PROCESS -> {
                 return ReportState.PROCESSED;
             }
         }
     }

}
