package com.ufrn.projeto.webApp.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.ufrn.projeto.webApp.dto.LogAlimentacaoRequestDTO;
import com.ufrn.projeto.webApp.entity.LogAlimentacao;
import com.ufrn.projeto.webApp.entity.Pet;
import com.ufrn.projeto.webApp.entity.Reservatorio;
import com.ufrn.projeto.webApp.entity.Usuario;
import com.ufrn.projeto.webApp.mapper.LogAlimentacaoMapper;
import com.ufrn.projeto.webApp.repository.LogAlimentacaoRepository;
import com.ufrn.projeto.webApp.repository.ReservatorioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogAlimentacaoService {

    private final LogAlimentacaoRepository repository;
    private final ReservatorioRepository reservatorioRepository;
    private final PetService petService;

    @Transactional(readOnly = true)
    public List<LogAlimentacao> getLogByPetId(UUID petId, Usuario usuario) {
        Pet pet = petService.getPetById(petId);
        if (pet == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found or doesn't exist");
        }

        if (!usuario.getId().equals(pet.getTutor().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to access this pet's logs");
        }

        return repository.getAllByPet(pet);
    }

    @Transactional
    public void deleteLog(UUID logId, Usuario usuario) {
        LogAlimentacao log = repository.getLogAlimentacaoById(logId);
        if (log == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Log not found or doesn't exist");
        }

        if (!log.getPet().getTutor().getId().equals(usuario.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to delete this log");
        }

        repository.delete(log);
    }

    @Transactional
    public LogAlimentacao createLog(UUID petId, LogAlimentacaoRequestDTO dto, Usuario usuario) {
        Pet pet = petService.getPetById(petId);
        if (pet == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found or doesn't exist");
        }

        if (!pet.getTutor().getId().equals(usuario.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to create a log for this pet");
        }

        LogAlimentacao log = LogAlimentacaoMapper.toEntity(dto);
        log.setPet(pet);

        Reservatorio reservatorio = reservatorioRepository.findById(1L)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservoir not found"));

        if (reservatorio.getGramasRestantes() < log.getGramasLiberadas()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient feed in reservoir");
        }

        reservatorio.setGramasRestantes(
                reservatorio.getGramasRestantes() - log.getGramasLiberadas()
        );
        reservatorioRepository.save(reservatorio);

        return repository.save(log);
    }
}
