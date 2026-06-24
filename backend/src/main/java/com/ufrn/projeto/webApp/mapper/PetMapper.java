package com.ufrn.projeto.webApp.mapper;

import com.ufrn.projeto.webApp.dto.PetRequestUpdateDTO;
import org.modelmapper.ModelMapper;

import com.ufrn.projeto.webApp.dto.PetDTO;
import com.ufrn.projeto.webApp.dto.PetRequestDTO;
import com.ufrn.projeto.webApp.entity.Pet;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
public class PetMapper {
	
	private static final ModelMapper mapper = new ModelMapper();
	
	public static PetDTO responseDTO(Pet pet) {
		return mapper.map(pet, PetDTO.class);
		
	}
	
	public static Pet toEntity(PetRequestDTO pet) {
		return mapper.map(pet, Pet.class);
		
	}

    public Pet updatePet(PetRequestUpdateDTO petDTO, Pet pet) {
        if (mapper.getTypeMap(PetRequestUpdateDTO.class, Pet.class) == null) {
            mapper.createTypeMap(PetRequestUpdateDTO.class, Pet.class)
                    .addMappings(mapper -> {
                        mapper.skip(Pet::setId);
                        mapper.skip(Pet::setTutor);
                    });
        }

        return mapper.map(petDTO, pet);
    }

}
