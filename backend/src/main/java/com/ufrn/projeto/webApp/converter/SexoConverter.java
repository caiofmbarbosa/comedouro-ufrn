package com.ufrn.projeto.webApp.converter;

import com.ufrn.projeto.webApp.enums.Sexo;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class SexoConverter implements AttributeConverter<Sexo, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Sexo sexo) {
        return sexo != null ? sexo.getId() : null;
    }

    @Override
    public Sexo convertToEntityAttribute(Integer integer) {
        return integer != null ? Sexo.fromNumero(integer) : null;
    }
}
