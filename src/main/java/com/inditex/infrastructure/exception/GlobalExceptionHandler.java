package com.inditex.infrastructure.exception;

import com.inditex.application.exception.DecryptionException;
import com.inditex.application.exception.PriceNotFoundException;
import com.inditex.application.exception.PriceServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    public static final String ERROR_MESSAGE = "error";
    public static final String ERROR_STATUS_MESSAGE = "message";
    public static final String ERROR_STATUS_CODE = "status";

    /**
     * Maneja la excepción PriceNotFoundException (precio no encontrado).
     */
    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePriceNotFoundException(PriceNotFoundException ex) {
        log.error("Error al buscar precios: {}", ex.getMessage(), ex);
        Map<String, Object> response = new HashMap<>();
        response.put(ERROR_MESSAGE, "No se encontraron precios.");
        response.put(ERROR_STATUS_MESSAGE, ex.getMessage());
        response.put(ERROR_STATUS_CODE, HttpStatus.NOT_FOUND.value());  // Código de estado 404.
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja la excepción PriceServiceException (error en el servicio de precios).
     */
    @ExceptionHandler(PriceServiceException.class)
    public ResponseEntity<Map<String, Object>> handlePriceServiceException(PriceServiceException ex) {
        log.error("Error en el servicio de precios: {}", ex.getMessage(), ex);
        Map<String, Object> response = new HashMap<>();
        response.put(ERROR_MESSAGE, "Error interno del servidor.");
        response.put(ERROR_STATUS_MESSAGE, ex.getMessage());
        response.put(ERROR_STATUS_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value());  // Código de estado 500.
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Maneja la excepción IllegalArgumentException (argumentos inválidos).
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("Solicitud incorrecta: {}", ex.getMessage(), ex);
        Map<String, Object> response = new HashMap<>();
        response.put(ERROR_MESSAGE, "Solicitud incorrecta.");
        response.put(ERROR_STATUS_MESSAGE, ex.getMessage());
        response.put(ERROR_STATUS_CODE, HttpStatus.BAD_REQUEST.value());  // Código de estado 400.
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja la excepción DecryptionException (error durante el proceso de desencriptación).
     */
    @ExceptionHandler(DecryptionException.class)
    public ResponseEntity<Map<String, Object>> handleDecryptionException(DecryptionException ex) {
        log.error("Error en el proceso de desencriptación: {}", ex.getMessage(), ex);
        Map<String, Object> response = new HashMap<>();
        response.put(ERROR_MESSAGE, "Credenciales inválidas");
        response.put(ERROR_STATUS_MESSAGE, ex.getMessage());
        response.put(ERROR_STATUS_CODE, HttpStatus.UNAUTHORIZED.value());  // Código de estado 401
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    /**
     * Maneja cualquier excepción inesperada.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        log.error("Error inesperado: {}", ex.getMessage(), ex);
        Map<String, Object> response = new HashMap<>();
        response.put(ERROR_MESSAGE, "Error inesperado.");
        response.put(ERROR_STATUS_MESSAGE, ex.getMessage());
        response.put(ERROR_STATUS_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value());  // Código de estado 500.
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
