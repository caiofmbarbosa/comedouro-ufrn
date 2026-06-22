package com.ufrn.projeto.webApp.repository;

import com.ufrn.projeto.webApp.entity.LogAlimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LogAlimentacaoRepository extends JpaRepository<LogAlimentacao, UUID> {
}
