package ru.anykeyers.videoservice.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateChannelDTO {

    /**
     * Название канала
     */
    @NotBlank(message = "you need to enter channel name")
    private String name;

    /**
     * Описание канала
     */
    private String description;
}
