package com.ufrn.projeto.webApp.repository;

import com.ufrn.projeto.webApp.entity.Reservatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservatorioRepository extends JpaRepository<Reservatorio, Long> {
}
