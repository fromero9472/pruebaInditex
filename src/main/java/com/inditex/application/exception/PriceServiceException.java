package com.inditex.application.exception;

/**
 * Excepción lanzada cuando ocurre un error en el servicio de precios, como fallos internos o errores no controlados.
 * Extiende {@link RuntimeException}, lo que la convierte en una excepción no verificada.
 */
public class PriceServiceException extends RuntimeException {

    /**
     * Constructor con mensaje de error y causa.
     *
     * @param message Descripción del error.
     * @param cause   Causa original de la excepción.
     */
    public PriceServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor con mensaje de error.
     *
     * @param message Descripción del error.
     */
    public PriceServiceException(String message) {
        super(message);
    }
}
