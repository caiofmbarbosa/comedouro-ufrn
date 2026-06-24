package com.ufrn.projeto.webApp.controller;

import com.ufrn.projeto.webApp.dto.LogAlimentacaoDTO;
import com.ufrn.projeto.webApp.dto.LogAlimentacaoRequestDTO;
import com.ufrn.projeto.webApp.entity.LogAlimentacao;
import com.ufrn.projeto.webApp.entity.Usuario;
import com.ufrn.projeto.webApp.mapper.LogAlimentacaoMapper;
import com.ufrn.projeto.webApp.service.LogAlimentacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/logs")
@RequiredArgsConstructor
public class LogAlimentacaoController {

    private final LogAlimentacaoService service;

    @GetMapping(value = "/{petId}")
    @PreAuthorize("hasRole('TUTOR')")
    public ResponseEntity<List<LogAlimentacaoDTO>> getPetsLog(
            @PathVariable("petId") UUID petId,
            @AuthenticationPrincipal Usuario usuario
    ) {
        List<LogAlimentacao> logsAlimentacao = service.getLogByPetId(petId, usuario);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(logsAlimentacao
                        .stream()
                        .map(LogAlimentacaoMapper::toDTO)
                        .toList());
    }

    @PostMapping(value = "/{petId}/new")
    @PreAuthorize("hasRole('TUTOR')")
    public ResponseEntity<LogAlimentacaoDTO> createPetsLog(
            @PathVariable(value = "petId") UUID petId,
            @Valid @RequestBody LogAlimentacaoRequestDTO dto,
            @AuthenticationPrincipal Usuario usuario
    ) {
        LogAlimentacao logAlimentacao = service.createLog(petId, dto, usuario);
        if (logAlimentacao == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(LogAlimentacaoMapper.toDTO(logAlimentacao));
    }

    @DeleteMapping(value = "/{logId}/delete")
    @PreAuthorize("hasRole('TUTOR')")
    public ResponseEntity<Void> deletePetsLog(
            @PathVariable(value = "logId") UUID logId,
            @AuthenticationPrincipal Usuario usuario
    ) {
        service.deleteLog(logId, usuario);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

}
