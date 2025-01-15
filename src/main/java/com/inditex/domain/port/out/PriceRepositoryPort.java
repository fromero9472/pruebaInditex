package com.inditex.domain.port.out;

import com.inditex.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Interfaz que define los métodos del repositorio de precios, utilizados para acceder y recuperar precios desde el almacenamiento de datos.
 * Esta interfaz forma parte del puerto de salida (Output Port) de la arquitectura hexagonal y define las operaciones
 * que el sistema ofrece para recuperar los precios desde la base de datos u otro almacenamiento.
 *
 * <p>Las implementaciones de esta interfaz proporcionan la lógica para obtener el precio más relevante para un producto,
 * basándose en los parámetros proporcionados como la marca, el producto y la fecha de aplicación.</p>
 */
public interface PriceRepositoryPort {

    /**
     * Busca el precio más relevante (con la mayor prioridad) para un producto y marca dados,
     * en la fecha de aplicación especificada.
     *
     * <p>Este método realiza una consulta sobre la base de datos o almacenamiento, y retorna el precio con la mayor prioridad
     * (si hay más de uno), ordenado de forma descendente por la fecha de inicio de la aplicación.</p>
     *
     * @param brandId       ID de la marca del producto.
     * @param productId     ID del producto cuya información de precio se busca.
     * @param startDate     Fecha de inicio para la cual se está buscando el precio.
     * @return Un {@link Optional} que contiene el precio más relevante encontrado, si existe.
     *         Si no se encuentra ningún precio que coincida con los parámetros, se retorna {@link Optional#empty()}.
     */
    Optional<Price> findTopPriceByBrandIdAndProductIdAndApplicationDate(
            Long brandId, Long productId, LocalDateTime startDate);
}
