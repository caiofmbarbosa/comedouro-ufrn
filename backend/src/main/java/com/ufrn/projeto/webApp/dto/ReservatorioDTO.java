package com.ufrn.projeto.webApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ReservatorioDTO {

    private Long id;
    private Integer capacidadeTotal;
    private Integer gramasRestantes;
    private LocalDateTime ultimaAtualizacao;
}
