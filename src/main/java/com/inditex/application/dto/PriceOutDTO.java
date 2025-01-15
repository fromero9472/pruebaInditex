package com.inditex.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Clase Data Transfer Object (DTO) que representa la salida de la consulta de precios.
 * Esta clase es utilizada para devolver los detalles del precio de un producto en base a los parámetros de entrada.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceOutDTO {

    /**
     * ID único del precio registrado.
     */
    private Long id;

    /**
     * ID de la marca asociada al precio.
     */
    private Long brandId;

    /**
     * Fecha y hora de inicio de la validez del precio.
     */
    private LocalDateTime startDate;

    /**
     * Fecha y hora de fin de la validez del precio.
     */
    private LocalDateTime endDate;

    /**
     * ID de la lista de precios en la que se encuentra este precio.
     */
    private Long priceList;

    /**
     * ID del producto al que se le aplica el precio.
     */
    private Long productId;

    /**
     * Prioridad de este precio. Los precios con mayor prioridad prevalecen sobre los de menor prioridad.
     */
    private Integer priority;

    /**
     * Monto del precio aplicado al producto.
     */
    private Double amount;

    /**
     * Moneda en la que está expresado el precio (por ejemplo, EUR, USD).
     */
    private String currency;

    /**
     * Constructor adicional para facilitar la creación de instancias de PriceOutDTO sin todos los campos.
     *
     * @param brandId ID de la marca.
     * @param startDate Fecha de inicio de validez.
     * @param endDate Fecha de fin de validez.
     * @param priceList ID de la lista de precios.
     * @param productId ID del producto.
     * @param priority Prioridad del precio.
     * @param price Monto del precio.
     * @param curr Moneda en la que se expresa el precio.
     */
    public PriceOutDTO(Long brandId, LocalDateTime startDate, LocalDateTime endDate, Long priceList, Long productId, Integer priority, Double price, String curr) {
        this.brandId = brandId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceList = priceList;
        this.productId = productId;
        this.priority = priority;
        this.amount = price;
        this.currency = curr;
    }
}
