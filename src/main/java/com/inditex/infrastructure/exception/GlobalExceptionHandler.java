package com.inditex.infrastructure.exception;

import com.inditex.domain.exception.PriceNotFoundException;
import com.inditex.domain.exception.PriceServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase global de manejo de excepciones para la aplicación.
 * Esta clase captura y maneja diferentes excepciones lanzadas en la aplicación
 * y devuelve respuestas personalizadas con el código de estado HTTP correspondiente.
 */
@Slf4j
@ControllerAdvice  // Indica que esta clase es responsable de manejar excepciones globalmente en la aplicación.
public class GlobalExceptionHandler {

    /**
     * Maneja la excepción PriceNotFoundException.
     * Se activa cuando no se encuentran precios para los parámetros proporcionados.
     *
     * @param ex La excepción capturada.
     * @return La respuesta con el código de error y mensaje correspondiente.
     */
    @ExceptionHandler(PriceNotFoundException.class)  // Anotación para capturar excepciones de tipo PriceNotFoundException.
    public ResponseEntity<Map<String, Object>> handlePriceNotFoundException(PriceNotFoundException ex) {
        log.error("Error al buscar precios: {}", ex.getMessage(), ex);  // Log de error con detalles de la excepción.
        Map<String, Object> response = new HashMap<>();  // Mapa para almacenar la información de la respuesta.

        // Agregamos los detalles del error en el mapa.
        response.put("error", "Sin contenido. No se encontraron precios para los parámetros proporcionados.");
        response.put("message", ex.getMessage());  // El mensaje de la excepción se incluye en la respuesta.
        response.put("status", HttpStatus.NOT_FOUND.value());  // El código de estado HTTP 404 para "Not Found".

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);  // Devolvemos la respuesta con el código de estado correspondiente.
    }

    /**
     * Maneja la excepción PriceServiceException.
     * Se activa cuando ocurre un error en el servicio de precios, como un fallo interno del servidor.
     *
     * @param ex La excepción capturada.
     * @return La respuesta con el código de error y mensaje correspondiente.
     */
    @ExceptionHandler(PriceServiceException.class)  // Anotación para capturar excepciones de tipo PriceServiceException.
    public ResponseEntity<Map<String, Object>> handlePriceServiceException(PriceServiceException ex) {
        log.error("Error en el servicio de precios: {}", ex.getMessage(), ex);  // Log de error con detalles de la excepción.
        Map<String, Object> response = new HashMap<>();  // Mapa para almacenar la información de la respuesta.

        // Agregamos los detalles del error en el mapa.
        response.put("error", "Error interno del servidor. Consulta los logs para más detalles.");
        response.put("message", ex.getMessage());  // El mensaje de la excepción se incluye en la respuesta.
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());  // El código de estado HTTP 500 para "Internal Server Error".

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);  // Devolvemos la respuesta con el código de estado correspondiente.
    }

    /**
     * Maneja la excepción IllegalArgumentException.
     * Se activa cuando los parámetros proporcionados en la solicitud son incorrectos o inválidos.
     *
     * @param ex La excepción capturada.
     * @return La respuesta con el código de error y mensaje correspondiente.
     */
    @ExceptionHandler(IllegalArgumentException.class)  // Anotación para capturar excepciones de tipo IllegalArgumentException.
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("Solicitud incorrecta: {}", ex.getMessage(), ex);  // Log de advertencia para solicitudes incorrectas.
        Map<String, Object> response = new HashMap<>();  // Mapa para almacenar la información de la respuesta.

        // Agregamos los detalles del error en el mapa.
        response.put("error", "Solicitud incorrecta. Verifica los parámetros.");  // Mensaje de error para solicitudes incorrectas.
        response.put("message", ex.getMessage());  // El mensaje de la excepción se incluye en la respuesta.
        response.put("status", HttpStatus.BAD_REQUEST.value());  // El código de estado HTTP 400 para "Bad Request".

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);  // Devolvemos la respuesta con el código de estado correspondiente.
    }

    /**
     * Maneja las excepciones no esperadas o genéricas.
     * Se activa para excepciones no especificadas anteriormente.
     *
     * @param ex La excepción capturada.
     * @return La respuesta con el código de error y mensaje correspondiente.
     */
    @ExceptionHandler(Exception.class)  // Anotación para capturar cualquier tipo de excepción general.
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        log.error("Error inesperado: {}", ex.getMessage(), ex);  // Log de error para excepciones genéricas.
        Map<String, Object> response = new HashMap<>();  // Mapa para almacenar la información de la respuesta.

        // Agregamos los detalles del error en el mapa.
        response.put("error", "Unexpected Error");  // Mensaje de error genérico para cualquier excepción inesperada.
        response.put("message", ex.getMessage());  // El mensaje de la excepción se incluye en la respuesta.
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());  // El código de estado HTTP 500 para "Internal Server Error".

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);  // Devolvemos la respuesta con el código de estado correspondiente.
    }
}
