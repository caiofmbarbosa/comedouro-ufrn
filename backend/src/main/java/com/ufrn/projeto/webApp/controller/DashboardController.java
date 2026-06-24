package com.ufrn.projeto.webApp.controller;

import com.ufrn.projeto.webApp.dto.DashboardDTO;
import com.ufrn.projeto.webApp.entity.Usuario;
import com.ufrn.projeto.webApp.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService service;

    @GetMapping
    @PreAuthorize("hasRole('TUTOR')")
    public ResponseEntity<List<DashboardDTO>> getDashboardData(
            @AuthenticationPrincipal Usuario usuario
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getDashboardData(usuario));

    }

}
