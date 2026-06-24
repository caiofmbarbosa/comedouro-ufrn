package com.ufrn.projeto.webApp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class LoginRequestDTO {
	
	@NotBlank(message = "O email deve ser fornecido.")
	private String email;
	
	@NotBlank(message = "A senha deve ser fornecida.")
	private String password;

}
