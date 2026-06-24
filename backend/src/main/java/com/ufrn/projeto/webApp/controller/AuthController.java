package com.ufrn.projeto.webApp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufrn.projeto.webApp.dto.UserLoginDTO;
import com.ufrn.projeto.webApp.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService service;
	
	@PostMapping("/login")
	public ResponseEntity<String> login(
			@Valid @RequestBody UserLoginDTO dto
			) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(service.login(dto));
	}

}
