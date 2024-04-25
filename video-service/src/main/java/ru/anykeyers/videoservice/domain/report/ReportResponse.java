package ru.anykeyers.videoservice.domain.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.anykeyers.videoservice.domain.dto.UserDTO;

/**
 * Данные для передачи ответа по жалобе
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {

    /**
     * Идентификатор заявки
     */
    private Long id;

    /**
     * Пользователь, на которого отправили жалобу
     */
    private UserDTO userTarget;

    /**
     * Отправитель жалобы
     */
    private UserDTO userSender;

    /**
     * Статус заявки
     */
    private ReportState reportState;

    /**
     * Текст жалобы
     */
    private String text;

    /**
     * Администратор, решающий проблему
     */
    private String solver;

    /**
     * Результат обработки жалобы
     */
    private String solveResult;

}
