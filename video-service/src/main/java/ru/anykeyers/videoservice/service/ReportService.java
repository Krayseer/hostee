package ru.anykeyers.videoservice.service;

import ru.anykeyers.videoservice.domain.report.ReportRequest;
import ru.anykeyers.videoservice.domain.report.ReportDTO;

import java.util.List;

/**
 * Сервис обработки жалоб
 */
public interface ReportService {

    /**
     * Получить список всех жалоб
     */
    List<ReportDTO> getAllReports();

    /**
     * Создать жалобу
     *
     * @param username      имя пользователя, который подал жалобу
     * @param reportRequest данные о жалобе
     */
    void createReport(String username, ReportRequest reportRequest);

    /**
     * Взять жалобу в обработку
     *
     * @param reportId      идентификатор жалобы
     * @param adminUsername имя пользователя администратора (обработчика ошибки)
     */
    void processReport(Long reportId, String adminUsername);

    /**
     * Записать результат проверки жалобы
     *
     * @param reportId      идентификатор жалобы
     * @param resultSolve   сообщение о результате проверки
     */
    void finishReport(Long reportId, String resultSolve);

}
