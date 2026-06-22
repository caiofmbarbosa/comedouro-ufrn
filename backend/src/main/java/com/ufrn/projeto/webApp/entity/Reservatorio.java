package com.ufrn.projeto.webApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "RESERVOIR")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Reservatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TOTAL_CAPACITY", nullable = false)
    private Integer capacidadeTotal;

    @Column(name = "REMAINING_GRAMS", nullable = false)
    private Integer gramasRestantes;

    @UpdateTimestamp
    @Column(name = "LAST_UPDATED_AT", nullable = false)
    private LocalDateTime ultimaAtualizacao = LocalDateTime.now();

}
