package com.ufrn.projeto.webApp.repository;

import com.ufrn.projeto.webApp.entity.LogAlimentacao;
import com.ufrn.projeto.webApp.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LogAlimentacaoRepository extends JpaRepository<LogAlimentacao, UUID> {
    List<LogAlimentacao> getAllByPet(Pet pet);

    LogAlimentacao getLogAlimentacaoById(UUID id);
}
