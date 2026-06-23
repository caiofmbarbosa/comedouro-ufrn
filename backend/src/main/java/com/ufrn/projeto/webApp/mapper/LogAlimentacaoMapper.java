package com.ufrn.projeto.webApp.mapper;

import com.ufrn.projeto.webApp.dto.LogAlimentacaoDTO;
import com.ufrn.projeto.webApp.dto.LogAlimentacaoRequestDTO;
import com.ufrn.projeto.webApp.entity.LogAlimentacao;
import org.modelmapper.ModelMapper;

public class LogAlimentacaoMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static LogAlimentacaoDTO toDTO(LogAlimentacao logAlimentacao) {
        return mapper.map(logAlimentacao, LogAlimentacaoDTO.class);
    }

    public static LogAlimentacao toEntity(LogAlimentacaoRequestDTO dto) {
        return mapper.map(dto, LogAlimentacao.class);
    }

}
