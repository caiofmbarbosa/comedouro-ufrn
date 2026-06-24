package com.ufrn.projeto.webApp.service;

import com.ufrn.projeto.webApp.dto.DashboardDTO;
import com.ufrn.projeto.webApp.entity.LogAlimentacao;
import com.ufrn.projeto.webApp.entity.Pet;
import com.ufrn.projeto.webApp.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    public List<DashboardDTO> getDashboardData(Usuario usuario) {
        return usuario.getPets()
                .stream()
                .map(this::toDashboardDTO)
                .toList();
    }

    private DashboardDTO toDashboardDTO(Pet pet) {
        DashboardDTO dto = new DashboardDTO();

        dto.setPetId(pet.getId());
        dto.setNomePet(pet.getName());
        dto.setRacaPet(pet.getRaca());
        dto.setPortePet(pet.getPorte());
        dto.setPesoPet(pet.getPeso());
        dto.setSexoPet(pet.getSexo());

        List<LogAlimentacao> logs = pet.getLogsAlimentacao();

        if (logs != null && !logs.isEmpty()) {
            LogAlimentacao ultimoLog = logs.stream()
                    .max(Comparator.comparing(LogAlimentacao::getDataHora))
                    .orElse(null);

            if (ultimoLog != null) {
                dto.setIdLog(ultimoLog.getId());
                dto.setGramasLiberadas(ultimoLog.getGramasLiberadas());
                dto.setDataHora(ultimoLog.getDataHora());
            }
        }

        return dto;
    }
}
