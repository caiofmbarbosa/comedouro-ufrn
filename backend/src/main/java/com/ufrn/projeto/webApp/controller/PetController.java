package com.ufrn.projeto.webApp.controller;

import com.ufrn.projeto.webApp.dto.PetDTO;
import com.ufrn.projeto.webApp.dto.PetRequestDTO;
import com.ufrn.projeto.webApp.dto.PetRequestUpdateDTO;
import com.ufrn.projeto.webApp.entity.Pet;
import com.ufrn.projeto.webApp.entity.Usuario;
import com.ufrn.projeto.webApp.mapper.PetMapper;
import com.ufrn.projeto.webApp.service.PetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pet")
@RequiredArgsConstructor
public class PetController {

    private final PetService service;

	@PostMapping(value = "/new")
    @PreAuthorize("hasRole('TUTOR')")
	public ResponseEntity<PetDTO> registerPet(
            @Valid @RequestBody PetRequestDTO dto,
            @AuthenticationPrincipal Usuario usuario
    ) {
        Pet pet = service.createPet(dto, usuario);
        if (pet == null || pet.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

		return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(PetMapper.responseDTO(pet));
	}

    @PutMapping(value = "/update/{petId}")
    @PreAuthorize("hasRole('TUTOR')")
    public ResponseEntity<PetDTO> updatePet(
            @PathVariable UUID petId,
            @Valid @RequestBody PetRequestUpdateDTO dto,
            @AuthenticationPrincipal Usuario usuario
    ) {
        Pet pet = service.updatePet(petId, dto, usuario);
        if (pet == null || pet.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(PetMapper.responseDTO(pet));
    }

    @DeleteMapping(value = "/delete/{petId}")
    @PreAuthorize("hasRole('TUTOR')")
    public ResponseEntity<Void> registerPet(
            @Valid @PathVariable(value = "petId") UUID petId,
            @AuthenticationPrincipal Usuario usuario
    ) {
        service.deletePet(petId, usuario);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}
