package com.inditex.domain.port.in;

import com.inditex.domain.model.PriceInDTO;
import com.inditex.domain.model.Price;

/**
 * Interfaz que define los métodos para obtener los precios de productos basados en parámetros específicos.
 * Forma parte del puerto de entrada (Input Port) en una arquitectura hexagonal.
 */
public interface GetSinglePrice {

    /**
     * Obtiene el precio de un producto según los parámetros proporcionados.
     *
     * @param params Objeto {@link PriceInDTO} que contiene la marca, el producto y la fecha de aplicación.
     * @return Objeto {@link Price} con el precio encontrado, o null si no se encuentra un precio válido.
     */
    Price getSinglePrice(PriceInDTO params);
}
