package ru.anykeyers.videoservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO с данными об ошибке
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO<T> {

    /**
     * Сообщение ошибки
     */
    private T data;

}
