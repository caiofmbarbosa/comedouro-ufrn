package com.ufrn.projeto.webApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class LogAlimentacaoRequestDTO {

    @NotBlank(message = "You must provide the grams released.")
    @Positive(message = "The grams released must be a positive number.")
    private Integer gramasLiberadas;

    @NotBlank(message = "You must provide the date of the log.")
    private LocalDateTime dataHora;

}
