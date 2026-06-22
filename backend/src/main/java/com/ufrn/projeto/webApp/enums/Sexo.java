package com.ufrn.projeto.webApp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum Sexo {
    MASCULINO(1, "Masculino"),
    FEMININO(2, "Feminino");

    private final Integer id;
    private final String descricao;

    public static Sexo fromNumero(Integer numero) {
        for (Sexo sexo : Sexo.values()) {
            if (Objects.equals(sexo.id, numero)) {
                return sexo;
            }
        }

        throw new IllegalArgumentException("Invalid num id: " + numero);
    }

}
