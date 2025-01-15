package com.inditex.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

/**
 * Configuración de seguridad para la aplicación.
 * Configura la autenticación, la autorización, el manejo de sesiones, y los filtros de seguridad.
 */
@Slf4j
@Configuration
@EnableWebSecurity  // Activa la configuración de seguridad web en la aplicación
public class SecurityConfig {

    // Filtro JWT para validar las solicitudes de autenticación
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    /**
     * Configura las reglas de seguridad de la aplicación, incluyendo las rutas permitidas, el manejo de sesiones y las excepciones.
     *
     * @param http Configuración de seguridad HTTP de Spring Security.
     * @return Un SecurityFilterChain que define las reglas de seguridad para la aplicación.
     * @throws Exception Si ocurre algún error de configuración de seguridad.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Configurando reglas de seguridad para las rutas de la aplicación"); // Log para indicar que la configuración de seguridad está iniciando

        http.csrf().disable()  // Desactiva la protección CSRF (Cross-Site Request Forgery), ya que se usa JWT
                .authorizeRequests()
                .antMatchers("/v1/auth/generate-token").permitAll()  // Permite acceso sin autenticación a esta ruta (generación de token)
                .antMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll() // Rutas Swagger públicas
                .anyRequest().authenticated()  // Requiere autenticación para todas las demás rutas
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Configura para no usar sesiones (stateless)
                .and()
                .exceptionHandling()
                // Manejador de acceso denegado: responde con un código 403 (Forbidden)
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    log.debug("Access Denied: " + request.getRequestURI());  // Log de acceso denegado
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
                });

        // Aplica el filtro JWT antes de que se ejecute el filtro de autenticación estándar (UsernamePasswordAuthenticationFilter)
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        log.info("Filtro JWT agregado correctamente antes del filtro de autenticación"); // Log para indicar que el filtro JWT fue añadido

        return http.build();  // Devuelve el objeto SecurityFilterChain configurado
    }

    /**
     * Configura el codificador de contraseñas utilizando el algoritmo BCrypt.
     *
     * @return El PasswordEncoder configurado con BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("Configurando PasswordEncoder con BCrypt"); // Log para indicar la configuración del encoder
        return new BCryptPasswordEncoder();  // BCrypt es un algoritmo seguro para encriptar contraseñas
    }

    /**
     * Configura el AuthenticationManager utilizado para autenticar a los usuarios.
     *
     * @param authConfig La configuración de autenticación proporcionada por Spring Security.
     * @return El AuthenticationManager configurado.
     * @throws Exception Si ocurre un error durante la configuración del AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        log.info("Configurando AuthenticationManager para la autenticación de usuarios"); // Log para indicar la configuración del AuthenticationManager
        return authConfig.getAuthenticationManager();  // Obtiene el AuthenticationManager del contexto de Spring Security
    }
}
