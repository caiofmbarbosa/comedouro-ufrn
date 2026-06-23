package com.ufrn.projeto.webApp.service;

import com.ufrn.projeto.webApp.dto.PetDTO;
import com.ufrn.projeto.webApp.dto.PetRequestDTO;
import com.ufrn.projeto.webApp.entity.Pet;
import com.ufrn.projeto.webApp.entity.Usuario;
import com.ufrn.projeto.webApp.mapper.PetMapper;
import com.ufrn.projeto.webApp.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;

    @Transactional
    public Pet createPet(PetRequestDTO petDTO, Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        Pet pet = PetMapper.toEntity(petDTO);
        pet.setTutor(usuario);

        return petRepository.save(pet);
    }

    @Transactional
    public void deletePet(UUID petId, Usuario usuario) {
        if (usuario == null) {
            return;
        }

        Pet pet = petRepository.getPetById(petId);
        if (!pet.getTutor().getId().equals(usuario.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can not exclude this pet.");
        }

        petRepository.delete(pet);
    }

    public Pet getPetById(UUID petId) {
        Pet pet = petRepository.getPetById(petId);
        if (pet == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found.");
        }

        return pet;
    }

}
