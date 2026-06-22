package com.ufrn.projeto.webApp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Porte {
    PEQUENO(1, "Pequeno"),
    MEDIO(2, "Médio"),
    GRANDE(3, "Grande");

    private final int valor;
    private final String descricao;

    public static Porte fromValue(int valor) {
        for (Porte porte : Porte.values()) {
            if (porte.valor == valor) {
                return porte;
            }
        }

        throw new IllegalArgumentException("Invalid value: " + valor);
    }

}
