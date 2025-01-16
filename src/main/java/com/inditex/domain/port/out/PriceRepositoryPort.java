package com.inditex.domain.port.out;

import com.inditex.domain.model.Price;

import java.time.LocalDateTime;

/**
 * Interfaz que define los métodos para acceder y recuperar precios desde el almacenamiento de datos.
 * Forma parte del puerto de salida (Output Port) en una arquitectura hexagonal.
 */
public interface PriceRepositoryPort {

    /**
     * Busca el precio más relevante (con mayor prioridad) para un producto y marca dados en una fecha específica.
     *
     * @param brandId   ID de la marca del producto.
     * @param productId ID del producto cuyo precio se busca.
     * @param startDate Fecha para la cual se consulta el precio.
     * @return El precio más relevante encontrado. Puede lanzar una excepción si no se encuentra.
     */
    Price findTopPriceByBrandIdAndProductIdAndApplicationDate(
            Long brandId, Long productId, LocalDateTime startDate);
}
