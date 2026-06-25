package com.ufrn.projeto.webApp.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ufrn.projeto.webApp.enums.Porte;
import com.ufrn.projeto.webApp.enums.Sexo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class PetRequestDTO {
	
		@NotBlank(message = "O nome do pet deve ser fornecido.")
	    private String name;
		
		@NotBlank(message = "A raça do pet deve ser fornecida.")
	    private String raca;
		
		@NotNull(message = "O porte do pet (pequeno, médio ou grande), deve ser fornecido.")
	    private Porte porte;
		
		@NotNull(message = "O peso atual do pet deve ser fornecido.")
	    private Double peso;
		
		@NotNull(message = "A data de nascimento do pet não pode estar vazia.")
		@JsonFormat(pattern = "yyyy-MM-dd")
	    private LocalDate dataNascimento;
		
		@NotNull(message = "O sexo do pet deve ser fornecido.")
	    private Sexo sexo;

}
