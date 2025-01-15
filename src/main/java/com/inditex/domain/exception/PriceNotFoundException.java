package com.inditex.domain.exception;

/**
 * Excepción personalizada que se lanza cuando no se encuentran precios disponibles
 * para los parámetros proporcionados (por ejemplo, producto, marca y fecha).
 * Esta excepción es parte del manejo de errores en la aplicación.
 *
 * <p>Extiende de {@link RuntimeException} para indicar que es una excepción no verificada
 * (unchecked exception), que no es obligatoria de manejar con un bloque try-catch.</p>
 */
public class PriceNotFoundException extends RuntimeException {

    /**
     * Constructor para crear una nueva excepción con un mensaje de error específico.
     *
     * @param message Mensaje detallado que describe la causa del error.
     */
    public PriceNotFoundException(String message) {
        super(message); // Se llama al constructor de la clase padre (RuntimeException)
    }

    /**
     * Constructor para crear una nueva excepción con un mensaje de error y una causa.
     * Este constructor es útil cuando se quiere asociar una excepción original a esta nueva
     * excepción para proporcionar más contexto sobre el error.
     *
     * @param message El mensaje detallado del error.
     * @param cause   La causa original de la excepción (puede ser otra excepción que se lanzó previamente).
     */
    public PriceNotFoundException(String message, Throwable cause) {
        super(message, cause); // Llama al constructor de RuntimeException con el mensaje y la causa.
    }
}
