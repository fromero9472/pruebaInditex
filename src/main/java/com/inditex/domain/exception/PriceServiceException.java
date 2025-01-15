package com.inditex.domain.exception;

/**
 * Excepción personalizada que se lanza cuando ocurre un error en el servicio relacionado con los precios.
 * Este tipo de excepción es utilizado para capturar fallos internos en el servicio, tales como problemas
 * con la lógica del negocio o errores no controlados durante la ejecución de las operaciones de precios.
 *
 * <p>Extiende de {@link RuntimeException}, lo que significa que es una excepción no verificada
 * (unchecked exception), y por lo tanto no es obligatorio manejarla con un bloque try-catch.</p>
 */
public class PriceServiceException extends RuntimeException {

    /**
     * Constructor para crear una nueva instancia de {@link PriceServiceException} con un mensaje de error y una causa.
     * Este constructor es útil cuando se desea asociar una excepción original (la causa) a la nueva excepción,
     * proporcionando más contexto sobre el error que ocurrió.
     *
     * @param message El mensaje detallado que describe la causa del error.
     * @param cause   La causa original de la excepción (por ejemplo, una excepción anterior que ocurrió).
     */
    public PriceServiceException(String message, Throwable cause) {
        super(message, cause); // Llama al constructor de RuntimeException con el mensaje y la causa.
    }

    /**
     * Constructor para crear una nueva instancia de {@link PriceServiceException} con un mensaje de error específico.
     *
     * @param message El mensaje detallado que describe la causa del error.
     */
    public PriceServiceException(String message) {
        super(message); // Llama al constructor de RuntimeException con el mensaje.
    }
}
