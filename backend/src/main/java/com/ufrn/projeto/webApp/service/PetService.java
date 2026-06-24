package com.ufrn.projeto.webApp.service;

import com.ufrn.projeto.webApp.dto.PetRequestDTO;
import com.ufrn.projeto.webApp.dto.PetRequestUpdateDTO;
import com.ufrn.projeto.webApp.entity.Pet;
import com.ufrn.projeto.webApp.entity.Usuario;
import com.ufrn.projeto.webApp.mapper.PetMapper;
import com.ufrn.projeto.webApp.mapper.PetUpdateMapper;
import com.ufrn.projeto.webApp.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository repository;
    private final PetUpdateMapper modelMapper;

    @Transactional
    public Pet createPet(PetRequestDTO petDTO, Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        Pet pet = PetMapper.toEntity(petDTO);
        pet.setTutor(usuario);

        return repository.save(pet);
    }

    @Transactional
    public void deletePet(UUID petId, Usuario usuario) {
        if (usuario == null) {
            return;
        }

        Pet pet = repository.getPetById(petId);
        if (!pet.getTutor().getId().equals(usuario.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can not exclude this pet.");
        }

        repository.delete(pet);
    }

    public Pet updatePet(UUID petId, PetRequestUpdateDTO dto) {
        Pet pet = repository.findById(petId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Pet not found"
                        ));

        modelMapper.updatePet(dto, pet);
        return repository.save(pet);
    }

    public Pet getPetById(UUID petId) {
        Pet pet = repository.getPetById(petId);
        if (pet == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found.");
        }

        return pet;
    }

}
