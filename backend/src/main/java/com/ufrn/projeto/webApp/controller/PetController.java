package com.ufrn.projeto.webApp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/pet")
@RequiredArgsConstructor
public class PetController {
	
	@PostMapping(value = "/new")
	public ResponseEntity<PetResponseDTO> registerPet() {
		
		
		
	}

}
