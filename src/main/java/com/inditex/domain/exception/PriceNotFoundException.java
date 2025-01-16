package com.inditex.domain.exception;

/**
 * Excepción lanzada cuando no se encuentran precios para los parámetros dados.
 * Extiende {@link RuntimeException} para ser una excepción no verificada.
 */
public class PriceNotFoundException extends RuntimeException {

    /**
     * Constructor con mensaje de error.
     *
     * @param message Descripción del error.
     */
    public PriceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje de error y causa.
     *
     * @param message Descripción del error.
     * @param cause   Causa original de la excepción.
     */
    public PriceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
