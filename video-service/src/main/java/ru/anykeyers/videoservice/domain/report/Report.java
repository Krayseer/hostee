package ru.anykeyers.videoservice.domain.report;

import jakarta.persistence.*;
import lombok.*;
import ru.anykeyers.videoservice.domain.User;

/**
 * Жалоба
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Отправитель жалобы
     */
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User userSender;

    /**
     * Пользователь, на которого отправлена жалоба
     */
    @ManyToOne
    @JoinColumn(name = "target_id", nullable = false)
    private User userTarget;

    /**
     * Текст жалобы
     */
    private String text;

    /**
     * Статус обработки
     */
    @Enumerated(EnumType.STRING)
    private ReportState reportState;

    /**
     * Администратор, которому принадлежит обработка жалобы
     */
    private String solver;

    /**
     * Итоговый ответ администратора
     */
    private String resultSolve;

}
