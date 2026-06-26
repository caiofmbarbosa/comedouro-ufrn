package com.ufrn.projeto.webApp.controller;

import com.ufrn.projeto.webApp.dto.ReservatorioDTO;
import com.ufrn.projeto.webApp.service.ReservatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reservatorio")
@RequiredArgsConstructor
public class ReservatorioController {

    private final ReservatorioService service;

    @GetMapping
    @PreAuthorize("hasRole('TUTOR')")
    public ResponseEntity<ReservatorioDTO> getReservatorio() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getReservatorio());
    }
}
