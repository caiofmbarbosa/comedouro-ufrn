package com.ufrn.projeto.webApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "FEEDING_LOGS")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class LogAlimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "FED_AT", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "GRAMS_RELEASED", nullable = false)
    private Integer gramasLiberadas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PET_ID", nullable = false)
    private Pet pet;

}
