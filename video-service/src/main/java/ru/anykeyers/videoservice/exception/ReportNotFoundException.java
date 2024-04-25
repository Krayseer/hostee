package ru.anykeyers.videoservice.exception;

/**
 * Исключение, сигнализирующее, что жалоба не существует
 */
public class ReportNotFoundException extends RuntimeException {

    public ReportNotFoundException(Long uuid) {
        super(String.format("Report not found with id: %s", uuid));
    }

}
