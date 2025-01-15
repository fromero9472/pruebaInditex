package com.inditex.domain.port.in;

import com.inditex.application.dto.PriceInDTO;
import com.inditex.application.dto.PriceOutDTO;

/**
 * Interfaz que define los métodos del servicio de precios, utilizado para la obtención de precios basados en los parámetros proporcionados.
 * Esta interfaz forma parte del puerto de entrada (Input Port) de la arquitectura hexagonal y define las operaciones
 * que el sistema ofrece para consultar precios.
 *
 * <p>Las implementaciones de esta interfaz proporcionan la lógica para obtener los precios para un producto determinado,
 * de acuerdo a la marca, el identificador del producto y la fecha de aplicación solicitada.</p>
 */
public interface PriceServicePort {

    /**
     * Método que obtiene el precio único aplicable a un producto dado, basándose en los parámetros proporcionados.
     *
     * @param params Un objeto de tipo {@link PriceInDTO} que contiene los parámetros necesarios para la consulta del precio,
     *               como el identificador de la marca, el identificador del producto y la fecha de aplicación.
     * @return Un objeto {@link PriceOutDTO} que contiene el precio encontrado, o null si no se encuentra un precio válido.
     *
     * <p>Este método es utilizado para consultar el precio de un producto, dado un conjunto de parámetros. Los parámetros
     * incluyen la marca, el producto y la fecha, lo que permite realizar la consulta de manera precisa.</p>
     */
    PriceOutDTO getSinglePrice(PriceInDTO params);
}
