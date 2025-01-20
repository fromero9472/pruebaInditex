package com.inditex.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO que representa la salida de la consulta de precios.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Price {

    private Long id;          // ID único del precio
    private Long brandId;     // ID de la marca asociada al precio
    private LocalDateTime startDate;  // Fecha de inicio de validez del precio
    private LocalDateTime endDate;    // Fecha de fin de validez del precio
    private Long priceList;   // ID de la lista de precios
    private Long productId;   // ID del producto
    private Integer priority; // Prioridad del precio
    private Double amount;    // Monto del precio
    private String currency;  // Moneda del precio

    /**
     * Constructor adicional para facilitar la creación del DTO sin todos los campos.
     */
    public Price(Long brandId, LocalDateTime startDate, LocalDateTime endDate, Long priceList, Long productId, Integer priority, Double price, String curr) {
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
