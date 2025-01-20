package com.inditex.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO que representa los parámetros necesarios para obtener el precio de un producto.
 */
@Data
@AllArgsConstructor
public class PriceInDTO {

    private Long productId;  // ID del producto
    private Long brandId;    // ID de la marca asociada al producto
    private String applicationDate;  // Fecha en formato 'YYYY-MM-DD HH:MM:SS'

    /**
     * Constructor por defecto, necesario para deserialización.
     */
    public PriceInDTO() {
    }
}
