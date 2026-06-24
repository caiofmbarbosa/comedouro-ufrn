package com.ufrn.projeto.webApp.dto;

import com.ufrn.projeto.webApp.enums.Porte;
import com.ufrn.projeto.webApp.enums.Sexo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class DashboardDTO {

    private UUID idLog;
    private Integer gramasLiberadas;
    private LocalDateTime dataHora;
    private UUID petId;
    private String nomePet;
    private String racaPet;
    private Porte portePet;
    private Double pesoPet;
    private Sexo sexoPet;

}
