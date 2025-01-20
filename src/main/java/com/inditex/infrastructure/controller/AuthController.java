package com.inditex.infrastructure.controller;

import com.inditex.domain.port.in.ValidateCredential;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@Tag(name = "Authentication", description = "Endpoints para la autenticación y generación de tokens JWT")
@RequestMapping("/v1/auth")
@Slf4j
public class AuthController {

    private final ValidateCredential authService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public AuthController(ValidateCredential authService) {
        this.authService = authService;
    }

    @Operation(
            summary = "Generar Token de Autenticación",
            description = "Genera un token JWT válido por 24 horas si las credenciales son correctas.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Token generado correctamente",
                            content = @Content(mediaType = "text/plain", schema = @Schema(example = "Bearer <token>"))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Credenciales inválidas",
                            content = @Content(mediaType = "text/plain", schema = @Schema(example = "Credenciales inválidas"))
                    )
            }
    )
    @PostMapping("/generate-token")
    public ResponseEntity<String> generateToken(
            @RequestParam @Schema(description = "Usuario del sistema", example = "usuario", required = true) String user,
            @RequestParam @Schema(description = "Contraseña del usuario", example = "password", required = true) String contrasena) {

        log.info("Generando token para el usuario: {}", user);

        if (authService.validateCredentials(user, contrasena)) {
            String token = Jwts.builder()
                    .setSubject(user)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 horas
                    .signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes())
                    .compact();

            log.info("Token generado exitosamente para el usuario: {}", user);
            return ResponseEntity.ok(token);
        } else {
            log.warn("Credenciales inválidas para el usuario: {}", user);
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }
}
