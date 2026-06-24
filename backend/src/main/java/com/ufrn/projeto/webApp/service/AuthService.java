package com.ufrn.projeto.webApp.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.ufrn.projeto.webApp.dto.LoginRequestDTO;
import com.ufrn.projeto.webApp.dto.LoginResponseDTO;
import com.ufrn.projeto.webApp.dto.RegisterDTO;
import com.ufrn.projeto.webApp.entity.Usuario;
import com.ufrn.projeto.webApp.mapper.UsuarioMapper;
import com.ufrn.projeto.webApp.repository.UserRepository;
import com.ufrn.projeto.webApp.security.TokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final TokenService tokenService;
	private final UsuarioMapper mapper;
	
	public LoginResponseDTO login(LoginRequestDTO dto) {
		Usuario usuario = repository.findByEmail(dto.getEmail())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email ou senha incorretos."));
		
		if (!passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
			throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Email ou senha incorretos."
            );
		}
		
		String accessToken = tokenService.generateAccessToken(usuario);
		
		LoginResponseDTO responseDTO = new LoginResponseDTO();
		responseDTO.setAccessToken(accessToken);
		
		return responseDTO;
	}

	@Transactional
	public LoginResponseDTO register(RegisterDTO dto) {
		if (repository.findByEmail(dto.getEmail()).isPresent()) {
		    throw new ResponseStatusException(
		            HttpStatus.CONFLICT,
		            "Usuário já cadastrado, recupere a senha."
		    );
		}
		
		if (!dto.getPassword().equals(dto.getConfirmPassword())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "As senhas não batem.");
		}
		
		Usuario newUser = mapper.mapToUsuarioEntity(dto);
		repository.save(newUser);
		
		String accessToken = tokenService.generateAccessToken(newUser);
		
		LoginResponseDTO responseDTO = new LoginResponseDTO();
		responseDTO.setAccessToken(accessToken);
		
		return responseDTO;
	}

}
