package com.ufrn.projeto.webApp.repository;

import com.ufrn.projeto.webApp.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PetRepository extends JpaRepository<Pet, UUID> {
    Pet getPetById(UUID id);
}
