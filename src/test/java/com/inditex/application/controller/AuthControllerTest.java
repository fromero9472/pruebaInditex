package com.inditex.application.controller;

import com.inditex.domain.port.in.AuthServicePort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ContextConfiguration
class AuthControllerTest {

    private AuthServicePort authServicePort;
    private AuthController authController;

    private final String jwtSecret = "dd53d7a7-0537-4c0c-9150-5fede45c8ce6"; // Valor manual

    @BeforeEach
    void setUp() {
        // Crear mock del servicio de autenticación
        authServicePort = Mockito.mock(AuthServicePort.class);

        // Instanciar el controlador con el mock
        authController = new AuthController(authServicePort);

        // Inyectar manualmente el valor del secret (simulación)
        authController.setJwtSecret(jwtSecret);
    }
    @Test
    void testGenerateToken_Success() {
        // Configuración de datos de prueba
        String username = "usuario123";
        String password = "password123";

        // Mockear el comportamiento del servicio
        when(authServicePort.validateCredentials(username, password)).thenReturn(true);

        // Llamar al método del controlador
        ResponseEntity<String> response = authController.generateToken(username, password);

        // Verificar el código de estado
        assertEquals(200, response.getStatusCodeValue());

        // Obtener el token
        String token = response.getBody();
        assertNotNull(token);

        // Validar que el token tenga el formato esperado (sin "Bearer ")
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret.getBytes())
                .parseClaimsJws(token)
                .getBody();

        // Verificar contenido del token
        assertEquals(username, claims.getSubject());
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());

        // Verificar que la expiración sea aproximadamente dentro de 24 horas
        long expirationMillis = claims.getExpiration().getTime();
        long issuedMillis = claims.getIssuedAt().getTime();
        assertTrue((expirationMillis - issuedMillis) <= 86400000);
    }

    @Test
    void testGenerateToken_InvalidCredentials() {
        // Configuración de datos de prueba
        String username = "usuario123";
        String password = "wrongpassword";

        // Mockear el comportamiento del servicio
        when(authServicePort.validateCredentials(username, password)).thenReturn(false);

        // Llamar al método del controlador
        ResponseEntity<String> response = authController.generateToken(username, password);

        // Verificar los resultados
        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Credenciales inválidas", response.getBody());
    }
}
