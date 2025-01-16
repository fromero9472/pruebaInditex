package com.inditex.domain.port.in;

import com.inditex.application.dto.PriceInDTO;
import com.inditex.application.dto.PriceOutDTO;

/**
 * Interfaz que define los métodos para obtener los precios de productos basados en parámetros específicos.
 * Forma parte del puerto de entrada (Input Port) en una arquitectura hexagonal.
 */
public interface PriceServicePort {

    /**
     * Obtiene el precio de un producto según los parámetros proporcionados.
     *
     * @param params Objeto {@link PriceInDTO} que contiene la marca, el producto y la fecha de aplicación.
     * @return Objeto {@link PriceOutDTO} con el precio encontrado, o null si no se encuentra un precio válido.
     */
    PriceOutDTO getSinglePrice(PriceInDTO params);
}
