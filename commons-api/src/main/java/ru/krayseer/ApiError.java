package ru.krayseer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Ошибка API
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError<T> {

    /**
     * Сообщение об ошибке
     */
    private T message;

}




