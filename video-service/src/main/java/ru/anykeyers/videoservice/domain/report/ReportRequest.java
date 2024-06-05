package ru.anykeyers.videoservice.domain.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO с данными о жалобе
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequest {

    /**
     * Имя пользователя на которого направлена жалоба
     */
    private String username;

    /**
     * Текст жалобы
     */
    private String text;

}
