package ru.anykeyers.videoservice.service;

import ru.anykeyers.videoservice.domain.report.ReportDTO;
import ru.anykeyers.videoservice.domain.report.ReportResponse;

import java.util.List;

/**
 * Сервис обработки жалоб
 */
public interface ReportService {

    /**
     * Получить список всех жалоб
     */
    List<ReportResponse> getAllReports();

    /**
     * Создать жалобу
     *
     * @param username  имя пользователя, который подал жалобу
     * @param reportDTO данные о жалобе
     */
    void createReport(String username, ReportDTO reportDTO);

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
