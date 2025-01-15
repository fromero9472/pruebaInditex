package com.inditex.application.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase Data Transfer Object (DTO) que representa los parámetros necesarios para obtener el precio de un producto
 * a partir del ID del producto, el ID de la marca y la fecha de aplicación del precio.
 * Esta clase es utilizada para enviar y recibir los datos entre las capas de la aplicación.
 */
@Data
@AllArgsConstructor
public class PriceInDTO {

    /**
     * ID del producto para el cual se desea consultar el precio.
     */
    private Long productId;

    /**
     * ID de la marca asociada al producto.
     */
    private Long brandId;

    /**
     * Fecha en la que se desea obtener el precio del producto, en formato 'YYYY-MM-DD HH:MM:SS'.
     */
    private String applicationDate;

    /**
     * Constructor por defecto necesario para las operaciones de deserialización.
     */
    public PriceInDTO() {
    }
}
