package com.inditex.infrastructure.exception;

import com.inditex.application.exception.PriceNotFoundException;
import com.inditex.application.exception.PriceServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    // Test para PriceNotFoundException
    @Test
    void testHandlePriceNotFoundException() {
        PriceNotFoundException ex = new PriceNotFoundException("No prices found for the given parameters.");

        ResponseEntity<Map<String, Object>> response = exceptionHandler.handlePriceNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().containsKey("error"));
        assertEquals("No se encontraron precios.", response.getBody().get("error"));
        assertTrue(response.getBody().containsKey("message"));
        assertEquals(ex.getMessage(), response.getBody().get("message"));
        assertTrue(response.getBody().containsKey("status"));
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().get("status"));
    }
    // Test para el constructor PriceNotFoundException(String, Throwable)
    @Test
    void testHandlePriceNotFoundExceptionWithCause() {
        // Crea una excepción de tipo Throwable que actuará como causa
        Throwable cause = new Exception("Cause of the error");

        // Crear la PriceNotFoundException con el mensaje y la causa
        PriceNotFoundException ex = new PriceNotFoundException("No prices found for the given parameters.", cause);

        // Llamar al manejador de excepciones
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handlePriceNotFoundException(ex);

        // Verificar el código de estado
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Verificar que el error y el mensaje sean correctos
        assertTrue(response.getBody().containsKey("error"));
        assertEquals("No se encontraron precios.", response.getBody().get("error"));
        assertTrue(response.getBody().containsKey("message"));
        assertEquals(ex.getMessage(), response.getBody().get("message"));
        assertTrue(response.getBody().containsKey("status"));
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().get("status"));

    }
    // Test para PriceServiceException
    @Test
    void testHandlePriceServiceException() {
        PriceServiceException ex = new PriceServiceException("Internal server error.");

        ResponseEntity<Map<String, Object>> response = exceptionHandler.handlePriceServiceException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().containsKey("error"));
        assertEquals("Error interno del servidor.", response.getBody().get("error"));
        assertTrue(response.getBody().containsKey("message"));
        assertEquals(ex.getMessage(), response.getBody().get("message"));
        assertTrue(response.getBody().containsKey("status"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().get("status"));
    }

    // Test para IllegalArgumentException
    @Test
    void testHandleIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("Invalid arguments.");

        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleIllegalArgumentException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().containsKey("error"));
        assertEquals("Solicitud incorrecta.", response.getBody().get("error"));
        assertTrue(response.getBody().containsKey("message"));
        assertEquals(ex.getMessage(), response.getBody().get("message"));
        assertTrue(response.getBody().containsKey("status"));
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().get("status"));
    }

    // Test para Exception genérica
    @Test
    void testHandleGenericException() {
        Exception ex = new Exception("Unexpected error");

        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleGenericException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().containsKey("error"));
        assertEquals("Error inesperado.", response.getBody().get("error"));
        assertTrue(response.getBody().containsKey("message"));
        assertEquals(ex.getMessage(), response.getBody().get("message"));
        assertTrue(response.getBody().containsKey("status"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().get("status"));
    }
}
