package com.ufrn.projeto.webApp.converter;

import com.ufrn.projeto.webApp.enums.Porte;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class PorteConverter implements AttributeConverter<Porte, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Porte porte) {
        return porte != null ? porte.getValor() : null;
    }

    @Override
    public Porte convertToEntityAttribute(Integer integer) {
        return integer == null ? null : Porte.fromValue(integer);
    }
}
