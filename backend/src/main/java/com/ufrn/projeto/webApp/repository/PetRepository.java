package com.ufrn.projeto.webApp.repository;

import com.ufrn.projeto.webApp.entity.Usuario;
import com.ufrn.projeto.webApp.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PetRepository extends JpaRepository<Pet, UUID> {
    Pet getPetById(UUID id);

    List<Pet> findAllByTutorAndAtivoTrueOrderByNameAsc(Usuario tutor);
}
