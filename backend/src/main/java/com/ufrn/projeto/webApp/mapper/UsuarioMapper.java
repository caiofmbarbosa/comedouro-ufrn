package com.ufrn.projeto.webApp.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ufrn.projeto.webApp.dto.RegisterDTO;
import com.ufrn.projeto.webApp.entity.Usuario;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsuarioMapper {
	
	private final ModelMapper mapper;
	private final PasswordEncoder passwordEncoder;
	
	public Usuario mapToUsuarioEntity(RegisterDTO dto) {
		Usuario usuario = new Usuario();
		
		if (mapper.getTypeMap(RegisterDTO.class, Usuario.class) == null) {
			mapper.createTypeMap(RegisterDTO.class, Usuario.class)
			.addMappings(mapper -> {
				mapper.skip(Usuario::setPets);
				mapper.skip(Usuario::setDataCadastro);
				mapper.skip(Usuario::setId);
				mapper.skip(Usuario::setAtivo);
			});
		}
		
		mapper.map(dto, usuario);
		usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
		
		return usuario;
	}

}
