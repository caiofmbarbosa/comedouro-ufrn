package com.ufrn.projeto.webApp.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class LogAlimentacaoRequestDTO {

    @NotNull(message = "You must provide the grams released.")
    @Positive(message = "The grams released must be a positive number.")
    private Integer gramasLiberadas;

    @NotNull(message = "You must provide the date of the log.")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "America/Sao_Paulo")
    private LocalDateTime dataHora;

}
