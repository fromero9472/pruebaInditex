package com.inditex.application.mapper;

import com.inditex.application.dto.PriceOutDTO;
import com.inditex.domain.model.Price;
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
     * Convierte una entidad {@link Price} a un DTO {@link PriceOutDTO}.
     *
     * @param product Entidad {@link Price} a convertir.
     * @return DTO {@link PriceOutDTO} resultante.
     */
    public PriceOutDTO convertToDTO(Price product) {
        log.info("Convirtiendo entidad Price a DTO: {}", product);
        PriceOutDTO dto = modelMapper.map(product, PriceOutDTO.class);
        log.info("Conversión exitosa. DTO resultante: {}", dto);
        return dto;
    }

    /**
     * Convierte un DTO {@link PriceOutDTO} a una entidad {@link Price}.
     *
     * @param dto DTO {@link PriceOutDTO} a convertir.
     * @return Entidad {@link Price} resultante.
     */
    public Price convertToEntity(PriceOutDTO dto) {
        log.info("Convirtiendo DTO PriceOutDTO a entidad: {}", dto);
        Price entity = modelMapper.map(dto, Price.class);
        log.info("Conversión exitosa. Entidad resultante: {}", entity);
        return entity;
    }
}
