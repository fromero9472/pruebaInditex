package com.inditex.application.controller;

import com.inditex.domain.port.in.AuthServicePort;
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

/**
 * Controlador para gestionar los endpoints relacionados con la autenticación de usuarios
 * y la generación de tokens JWT. Este controlador proporciona un endpoint para generar un token
 * si las credenciales del usuario son válidas.
 */
@RestController
@Tag(name = "Authentication", description = "Endpoints para gestionar la autenticación y generación de tokens")
@RequestMapping("/v1/auth")
@Slf4j  // Anotación de Lombok para habilitar logging en la clase.
public class AuthController {

    private final AuthServicePort authService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    /**
     * Constructor que inyecta el servicio de autenticación.
     * @param authService Servicio para validar las credenciales del usuario.
     */
    public AuthController(AuthServicePort authService) {
        this.authService = authService;
    }

    /**
     * Endpoint para generar un token JWT.
     *
     * @param user Nombre del usuario.
     * @param contrasena Contraseña del usuario.
     * @return El token JWT si las credenciales son válidas, o un mensaje de error si no lo son.
     *
     * <p>Este método valida las credenciales proporcionadas por el usuario. Si son correctas,
     * genera un token JWT que será válido por 24 horas. Si las credenciales no son correctas,
     * devuelve un error de autenticación.</p>
     */
    @Operation(
            summary = "Generar Token de Autenticación",
            description = "Genera un token JWT válido por 24 horas si las credenciales proporcionadas son correctas.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Token generado exitosamente",
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

        log.info("Generando token para el usuario: {}", user);  // Log para rastrear el inicio del proceso.

        // Validación de las credenciales proporcionadas por el usuario
        if (authService.validateCredentials(user, contrasena)) {
            log.info("Credenciales válidas para el usuario: {}", user);  // Log cuando las credenciales son válidas.

            // Si las credenciales son válidas, se genera el token JWT
            String token = Jwts.builder()
                    .setSubject(user) // Asocia el nombre de usuario al token
                    .setIssuedAt(new Date()) // Establece la fecha de emisión del token
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Establece la expiración a 24 horas
                    .signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes()) // Firma el token con el algoritmo HS256 y la clave secreta
                    .compact(); // Genera el token

            log.info("Token generado exitosamente para el usuario: {}", user);  // Log de éxito cuando se genera el token.

            return ResponseEntity.ok(token); // Retorna el token en una respuesta 200 OK
        } else {
            log.warn("Credenciales inválidas para el usuario: {}", user);  // Log de advertencia cuando las credenciales son inválidas.

            // Si las credenciales no son válidas, retorna un error 401
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

    /**
     * Establece el secreto del JWT.
     * Este método es útil para la inyección de dependencias en pruebas.
     *
     * @param jwtSecret La clave secreta utilizada para firmar el token JWT.
     */
    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }
}
