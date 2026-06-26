package com.ufrn.projeto.webApp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.ufrn.projeto.webApp.enums.Porte;
import com.ufrn.projeto.webApp.enums.Sexo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class PetDTO {

    private UUID id;
    private String name;
    private String raca;
	    private Porte porte;
	    private BigDecimal peso;
	    private LocalDate dataNascimento;
	    private Sexo sexo;

}
