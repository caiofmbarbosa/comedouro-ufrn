package com.ufrn.projeto.webApp.entity;

import com.ufrn.projeto.webApp.converter.PorteConverter;
import com.ufrn.projeto.webApp.converter.SexoConverter;
import com.ufrn.projeto.webApp.enums.Porte;
import com.ufrn.projeto.webApp.enums.Sexo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "PETS")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @Column(name = "BREED", nullable = false, length = 50)
    private String raca;

    @Convert(converter = PorteConverter.class)
    @Column(name = "PET_SIZE", nullable = false)
    private Porte porte;

    @Column(name = "WEIGHT", nullable = false)
    private BigDecimal peso;

    @Column(name = "BIRTH_DATE", nullable = false)
    private LocalDate dataNascimento;

    @Convert(converter = SexoConverter.class)
    @Column(name = "GENDER", nullable = false)
    private Sexo sexo;

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean ativo = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TUTOR_ID", nullable = false)
    private Usuario tutor;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LogAlimentacao> logsAlimentacao;

}
