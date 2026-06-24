package com.ufrn.projeto.webApp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RegisterDTO {
	
	@NotBlank(message = "O email deve ser fornecido.")
	private String email;
	
	@NotBlank(message = "A senha deve ser fornecida.")
	private String password;
	
	@NotBlank(message = "A confirmação de senha deve estar preenchida.")
	private String confirmPassword;
	
	@NotBlank(message = "Informe um nome.")
	private String nome;

}
