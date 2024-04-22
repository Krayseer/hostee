package ru.anykeyers.videoservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO с данными о JWT токене пользователя
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {

    /**
     * JWT токен
     */
    private String jwtToken;

}
