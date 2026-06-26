package com.ufrn.projeto.webApp.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.ufrn.projeto.webApp.dto.PetRequestDTO;
import com.ufrn.projeto.webApp.dto.PetRequestUpdateDTO;
import com.ufrn.projeto.webApp.entity.Pet;
import com.ufrn.projeto.webApp.entity.Usuario;
import com.ufrn.projeto.webApp.mapper.PetMapper;
import com.ufrn.projeto.webApp.mapper.PetUpdateMapper;
import com.ufrn.projeto.webApp.repository.PetRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository repository;
    private final PetUpdateMapper modelMapper;

    @Transactional
    public Pet createPet(PetRequestDTO petDTO, Usuario usuario) {
        Pet pet = PetMapper.toEntity(petDTO);
        pet.setTutor(usuario);

        return repository.save(pet);
    }

    @Transactional(readOnly = true)
    public List<Pet> getPetsByTutor(Usuario usuario) {
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User must be authenticated.");
        }

        return repository.findAllByTutorAndAtivoTrueOrderByNameAsc(usuario);
    }

    @Transactional
    public void deletePet(UUID petId, Usuario usuario) {
        Pet pet = repository.getPetById(petId);
        if (pet == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found");
        }

        if (!pet.getTutor().getId().equals(usuario.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can not exclude this pet.");
        }

        repository.delete(pet);
    }

    @Transactional
    public Pet updatePet(UUID petId, PetRequestUpdateDTO dto, Usuario usuario) {
        Pet pet = repository.findById(petId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Pet not found"
                        ));
        if (!usuario.getId().equals(pet.getTutor().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can not update this pet.");
        }

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
