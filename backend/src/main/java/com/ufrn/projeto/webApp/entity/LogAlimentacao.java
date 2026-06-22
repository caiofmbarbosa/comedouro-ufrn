package com.ufrn.projeto.webApp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ALIMENTATION_LOG")
@Getter @Setter
public class LogAlimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DATE_HOUR", nullable = false)
    private LocalDateTime dataHora;

}
