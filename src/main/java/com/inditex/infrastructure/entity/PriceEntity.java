package com.inditex.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad que representa un precio y mapea la tabla 'PRICES' en la base de datos.
 */
@Entity
@Table(name = "PRICES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BRAND_ID")
    private Long brandId;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "PRICE_LIST")
    private Long priceList;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "PRICE")
    private Double amount;

    @Column(name = "CURR")
    private String currency;

    /**
     * Constructor con parámetros.
     *
     * @param brandId   El ID de la marca.
     * @param startDate La fecha de inicio de validez del precio.
     * @param endDate   La fecha de fin de validez del precio.
     * @param priceList El ID de la lista de precios.
     * @param productId El ID del producto.
     * @param priority  La prioridad del precio.
     * @param price     El valor del precio.
     * @param curr      La moneda del precio.
     */
    public PriceEntity(Long brandId, LocalDateTime startDate, LocalDateTime endDate, Long priceList, Long productId, Integer priority, Double price, String curr) {
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
