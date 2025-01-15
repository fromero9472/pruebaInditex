package com.inditex.infrastructure.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * Filtro que intercepta las solicitudes HTTP para validar el token JWT y establecer la autenticación en el contexto de seguridad de la aplicación.
 * Este filtro se ejecuta una vez por solicitud y asegura que las peticiones autenticadas con JWT sean validadas correctamente.
 */
@Slf4j
@Configuration
public class JwtRequestFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String jwtSecret; // Clave secreta utilizada para firmar y verificar el JWT

    /**
     * Método que intercepta la solicitud HTTP, valida el JWT, y si es válido, establece el contexto de seguridad.
     * Este método se ejecuta para cada solicitud que pase por este filtro.
     *
     * @param request La solicitud HTTP entrante.
     * @param response La respuesta HTTP que se enviará de vuelta al cliente.
     * @param chain La cadena de filtros de la solicitud.
     * @throws ServletException Si ocurre un error en el filtro.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Recupera el encabezado Authorization de la solicitud
        final String authorizationHeader = request.getHeader("Authorization");

        // Si el encabezado Authorization está presente y comienza con "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extraer el token JWT del encabezado Authorization
            String jwt = authorizationHeader.substring(7);

            try {
                // Parsear el JWT y obtener las claims (afirmaciones) dentro del token
                Claims claims = Jwts.parser()
                        .setSigningKey(jwtSecret.getBytes()) // Clave secreta para verificar la firma del token
                        .parseClaimsJws(jwt) // Parsear el JWT
                        .getBody(); // Obtener el cuerpo del token (las claims)

                // Obtener el nombre de usuario del token (subject)
                String username = claims.getSubject();

                // Si el nombre de usuario está presente y no hay autenticación previa en el contexto de seguridad
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Crear un token de autenticación sin contraseña y con roles vacíos
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    username,
                                    null,
                                    Collections.emptyList() // Lista vacía de roles/authorities, podría ser mejor adaptarlo
                            );

                    // Establecer el token de autenticación en el contexto de seguridad
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.info("Autenticación exitosa para el usuario: {}", username); // Log de éxito
                }

            } catch (Exception e) {
                // Si el JWT es inválido o ha expirado
                log.warn("Token inválido o expirado: {}", e.getMessage()); // Log de advertencia con el mensaje de error

                // Responder con un código de estado 401 (no autorizado) y un mensaje de error
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token inválido o expirado");
                return;  // Detener la cadena de filtros si el token no es válido
            }
        } else {
            log.debug("No se proporcionó un encabezado de autorización con el prefijo 'Bearer'"); // Log de debug
        }

        // Continuar con el siguiente filtro de la cadena
        chain.doFilter(request, response);
    }
}
