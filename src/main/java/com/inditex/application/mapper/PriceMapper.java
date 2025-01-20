package com.inditex.application.mapper;

import com.inditex.domain.model.Price;
import com.inditex.infrastructure.entity.PriceEntity;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapea entre las entidades de dominio y los DTOs.
 */
@Component
@Slf4j
public class PriceMapper {

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Convierte una entidad {@link PriceEntity} a un DTO {@link Price}.
     *
     * @param product Entidad {@link PriceEntity} a convertir.
     * @return DTO {@link Price} resultante.
     */
    public Price convertToDTO(PriceEntity product) {
        log.info("Convirtiendo entidad Price a DTO: {}", product);
        Price dto = modelMapper.map(product, Price.class);
        log.info("Conversión exitosa. DTO resultante: {}", dto);
        return dto;
    }

    /**
     * Convierte un DTO {@link Price} a una entidad {@link PriceEntity}.
     *
     * @param dto DTO {@link Price} a convertir.
     * @return Entidad {@link PriceEntity} resultante.
     */
    public PriceEntity convertToEntity(Price dto) {
        log.info("Convirtiendo DTO PriceOutDTO a entidad: {}", dto);
        PriceEntity entity = modelMapper.map(dto, PriceEntity.class);
        log.info("Conversión exitosa. Entidad resultante: {}", entity);
        return entity;
    }
}
