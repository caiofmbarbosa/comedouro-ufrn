package com.ufrn.projeto.webApp.dto;

import com.ufrn.projeto.webApp.enums.Porte;
import com.ufrn.projeto.webApp.enums.Sexo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class PetRequestUpdateDTO {
	
	    private String name;
	    private String raca;
	    private Porte porte;
	    private BigDecimal peso;
	    private LocalDate dataNascimento;
	    private Sexo sexo;

}
