package com.inditex.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    private static final String ENCRYPTED_USER = "CKnCRiqiG9q26l6933J4pL+VzzqhrUfxIjlbN3JTCCHZZnXyGhMgJyAigK5MmCwS9FQFQY9pmimXG1ykZ3ILsQ==";
    private static final String ENCRYPTED_PASSWORD = "MHhyRvunpZi3aDUAdCys5vVfi0/ermOgNjOC7DZnUoK3D+1uYDJpP/pevaMVqC8yjYhZDZ3BNS/EI9U6ao/6GQ==";

    private static final String MOCK_USER = "usuario";
    private static final String MOCK_PASSWORD = "password";

    private PrivateKey privateKey;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Leer clave privada desde resources
        byte[] privateKeyBytes = Files.readAllBytes(Paths.get("src/main/resources/key/private_key.pem"));
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        privateKey = keyFactory.generatePrivate(keySpec);

        // Insertar valores en el servicio con ReflectionTestUtils
        ReflectionTestUtils.setField(authService, "jwtUser", ENCRYPTED_USER);
        ReflectionTestUtils.setField(authService, "jwtPassword", ENCRYPTED_PASSWORD);
    }

    @Test
    void validateCredentials_ValidUserAndPassword_ReturnsTrue() {
        boolean result = authService.validateCredentials(MOCK_USER, MOCK_PASSWORD);

        assertTrue(result, "Las credenciales válidas deberían ser aceptadas.");
    }

    @Test
    void validateCredentials_InvalidUser_ReturnsFalse() {
        boolean result = authService.validateCredentials("usuario_incorrecto", MOCK_PASSWORD);

        assertFalse(result, "Un usuario incorrecto no debería ser aceptado.");
    }

    @Test
    void validateCredentials_InvalidPassword_ReturnsFalse() {
        boolean result = authService.validateCredentials(MOCK_USER, "password_incorrecto");

        assertFalse(result, "Una contraseña incorrecta no debería ser aceptada.");
    }

    @Test
    void validateCredentials_InvalidUserAndPassword_ReturnsFalse() {
        boolean result = authService.validateCredentials("usuario_incorrecto", "password_incorrecto");

        assertFalse(result, "Credenciales incorrectas no deberían ser aceptadas.");
    }

    @Test
    void validateCredentials_ExceptionDuringDecryption_ReturnsFalse() throws Exception {
        // Insertar una clave inválida simulando un fallo de desencriptación
        ReflectionTestUtils.setField(authService, "jwtUser", "invalidEncryptedValue");

        boolean result = authService.validateCredentials(MOCK_USER, MOCK_PASSWORD);

        assertFalse(result, "Si ocurre una excepción durante la desencriptación, debe retornar false.");
    }
}
