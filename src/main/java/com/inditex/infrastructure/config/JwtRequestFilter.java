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

@Slf4j
@Configuration
public class JwtRequestFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String jwtSecret; // Clave secreta para la validación del JWT

    /**
     * Intercepta la solicitud HTTP, valida el JWT y establece la autenticación en el contexto de seguridad.
     *
     * @param request La solicitud HTTP entrante.
     * @param response La respuesta HTTP.
     * @param chain La cadena de filtros.
     * @throws ServletException Si ocurre un error en el filtro.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Obtener el encabezado Authorization
        final String authorizationHeader = request.getHeader("Authorization");

        // Verificar si el encabezado Authorization está presente y es un Bearer Token
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extraer el JWT
            String jwt = authorizationHeader.substring(7);

            try {
                // Validar el JWT y obtener las claims
                Claims claims = Jwts.parser()
                        .setSigningKey(jwtSecret.getBytes())
                        .parseClaimsJws(jwt)
                        .getBody();

                // Obtener el nombre de usuario del token
                String username = claims.getSubject();

                // Si el usuario está presente y no hay autenticación previa
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Crear un token de autenticación sin contraseña ni roles
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());

                    // Establecer el token de autenticación en el contexto de seguridad
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.info("Autenticación exitosa para el usuario: {}", username);
                }

            } catch (Exception e) {
                // Si el JWT es inválido o ha expirado
                log.warn("Token inválido o expirado: {}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token inválido o expirado");
                return;  // Detener la cadena de filtros
            }
        } else {
            log.debug("No se proporcionó un encabezado de autorización con el prefijo 'Bearer'");
        }

        // Continuar con el siguiente filtro
        chain.doFilter(request, response);
    }
}
