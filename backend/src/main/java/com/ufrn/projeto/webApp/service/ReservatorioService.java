package com.ufrn.projeto.webApp.service;

import com.ufrn.projeto.webApp.dto.ReservatorioDTO;
import com.ufrn.projeto.webApp.entity.Reservatorio;
import com.ufrn.projeto.webApp.repository.ReservatorioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ReservatorioService {

    private final ReservatorioRepository repository;

    @Transactional(readOnly = true)
    public ReservatorioDTO getReservatorio() {
        Reservatorio reservatorio = repository.findById(1L)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservoir not found"));

        return new ReservatorioDTO(
                reservatorio.getId(),
                reservatorio.getCapacidadeTotal(),
                reservatorio.getGramasRestantes(),
                reservatorio.getUltimaAtualizacao()
        );
    }
}
