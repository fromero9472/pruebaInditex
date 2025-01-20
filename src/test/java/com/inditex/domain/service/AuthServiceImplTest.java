package com.inditex.domain.service;

import com.inditex.application.service.AuthService;
import com.inditex.domain.port.out.CredentialValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthServiceImplTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private CredentialValidator credentialValidator;

    private static final String MOCK_USER = "usuario";
    private static final String MOCK_PASSWORD = "password";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateCredentials_ValidUserAndPassword_ReturnsTrue() {
        when(credentialValidator.validate(MOCK_USER, MOCK_PASSWORD)).thenReturn(true);

        boolean result = authService.validateCredentials(MOCK_USER, MOCK_PASSWORD);

        assertTrue(result, "Las credenciales válidas deberían ser aceptadas.");
    }

    @Test
    void validateCredentials_InvalidUser_ReturnsFalse() {
        when(credentialValidator.validate("usuario_incorrecto", MOCK_PASSWORD)).thenReturn(false);

        boolean result = authService.validateCredentials("usuario_incorrecto", MOCK_PASSWORD);

        assertFalse(result, "Un usuario incorrecto no debería ser aceptado.");
    }

    @Test
    void validateCredentials_InvalidPassword_ReturnsFalse() {
        when(credentialValidator.validate(MOCK_USER, "password_incorrecto")).thenReturn(false);

        boolean result = authService.validateCredentials(MOCK_USER, "password_incorrecto");

        assertFalse(result, "Una contraseña incorrecta no debería ser aceptada.");
    }

    @Test
    void validateCredentials_InvalidUserAndPassword_ReturnsFalse() {
        when(credentialValidator.validate("usuario_incorrecto", "password_incorrecto")).thenReturn(false);

        boolean result = authService.validateCredentials("usuario_incorrecto", "password_incorrecto");

        assertFalse(result, "Credenciales incorrectas no deberían ser aceptadas.");
    }
}
