package com.ufrn.projeto.webApp.mapper;

import com.ufrn.projeto.webApp.dto.PetRequestUpdateDTO;
import com.ufrn.projeto.webApp.entity.Pet;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PetUpdateMapper {
	
	private final ModelMapper mapper;

    public Pet updatePet(PetRequestUpdateDTO petDTO, Pet pet) {
        if (mapper.getTypeMap(PetRequestUpdateDTO.class, Pet.class) == null) {
            mapper.createTypeMap(PetRequestUpdateDTO.class, Pet.class)
                    .addMappings(mapper -> {
                        mapper.skip(Pet::setId);
                        mapper.skip(Pet::setTutor);
                    });
        }

        mapper.map(petDTO, pet);
        return pet;
    }

}
