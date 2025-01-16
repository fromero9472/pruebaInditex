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

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    /**
     * Configura las reglas de seguridad de la aplicación.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Configurando reglas de seguridad.");

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/v1/auth/generate-token").permitAll()  // Permite acceso sin autenticación
                .antMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()  // Rutas Swagger públicas
                .anyRequest().authenticated()  // Requiere autenticación para las demás rutas
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    log.debug("Access Denied: " + request.getRequestURI());
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
                });

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        log.info("Filtro JWT agregado.");

        return http.build();
    }

    /**
     * Configura el codificador de contraseñas con BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("Configurando PasswordEncoder con BCrypt.");
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura el AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        log.info("Configurando AuthenticationManager.");
        return authConfig.getAuthenticationManager();
    }
}
