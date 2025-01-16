package com.inditex.infrastructure.config;

import com.inditex.infrastructure.security.JwtRequestFilter;
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

    @SuppressWarnings("squid:S2228") // Esta es la regla para deshabilitar CSRF en Sonar
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Configurando reglas de seguridad.");

        // Deshabilitamos CSRF porque usamos JWT para la autenticación. Las solicitudes se validan con el token en el encabezado Authorization.
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless, sin sesiones
                .and()
                .authorizeRequests()
                .antMatchers("/v1/auth/generate-token").permitAll() // Permitir acceso público al login
                .antMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll() // Permitir acceso público a Swagger
                .anyRequest().authenticated() // Requerir autenticación para cualquier otra ruta
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Agregar filtro JWT antes del filtro de autenticación predeterminado

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
