package com.inditex.application.mapper;

import com.inditex.application.dto.PriceOutDTO;
import com.inditex.domain.model.Price;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Clase encargada de mapear entre las entidades de dominio y los DTOs (Data Transfer Objects).
 * Facilita la conversión de objetos entre las capas de la aplicación para separar las preocupaciones.
 */
@Component
@Slf4j  // Anotación de Lombok para habilitar logging en la clase.
public class PriceMapper {

    /**
     * Instancia de ModelMapper utilizada para realizar el mapeo de objetos entre entidades y DTOs.
     */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Convierte un objeto de tipo {@link Price} (entidad) a un {@link PriceOutDTO} (DTO).
     * Este método mapea los atributos de la entidad {@link Price} a su correspondiente DTO.
     *
     * @param product Objeto de tipo {@link Price} que representa la entidad de dominio.
     * @return Un objeto {@link PriceOutDTO} con los datos de la entidad {@link Price}.
     */
    public PriceOutDTO convertToDTO(Price product) {
        log.info("Convirtiendo entidad Price a DTO: {}", product);  // Log para rastrear la conversión de la entidad.
        PriceOutDTO dto = modelMapper.map(product, PriceOutDTO.class);
        log.info("Conversión exitosa. DTO resultante: {}", dto);  // Log para confirmar que la conversión fue exitosa.
        return dto;
    }

    /**
     * Convierte un objeto de tipo {@link PriceOutDTO} a un {@link Price} (entidad).
     * Este método mapea los atributos del DTO {@link PriceOutDTO} a su correspondiente entidad.
     *
     * @param dto Objeto de tipo {@link PriceOutDTO} que representa los datos a convertir a entidad.
     * @return Un objeto {@link Price} con los datos provenientes del DTO {@link PriceOutDTO}.
     */
    public Price convertToEntity(PriceOutDTO dto) {
        log.info("Convirtiendo DTO PriceOutDTO a entidad: {}", dto);  // Log para rastrear la conversión del DTO.
        Price entity = modelMapper.map(dto, Price.class);
        log.info("Conversión exitosa. Entidad resultante: {}", entity);  // Log para confirmar que la conversión fue exitosa.
        return entity;
    }
}
