package com.ufrn.projeto.webApp.mapper;

import org.modelmapper.ModelMapper;

import com.ufrn.projeto.webApp.dto.PetDTO;
import com.ufrn.projeto.webApp.dto.PetRequestDTO;
import com.ufrn.projeto.webApp.entity.Pet;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PetMapper {
	
	private static final ModelMapper mapper = new ModelMapper();
	
	public static PetDTO responseDTO(Pet pet) {
		return mapper.map(pet, PetDTO.class);
		
	}
	
	public static Pet toEntity(PetRequestDTO pet) {
		return mapper.map(pet, Pet.class);
		
	}

}
