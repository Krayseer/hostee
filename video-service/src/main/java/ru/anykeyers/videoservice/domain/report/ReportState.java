package ru.anykeyers.videoservice.domain.report;

/**
 * Статус жалобы
 */
public enum ReportState {

    /**
     * Ожидает обработки
     */
    WAIT_PROCESS,
    /**
     * Находится в обработке
     */
    IN_PROCESS,
    /**
     * Обработана
     */
    PROCESSED

}
