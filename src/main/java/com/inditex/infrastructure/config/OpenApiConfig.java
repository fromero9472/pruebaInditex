package com.inditex.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI para la documentación de la API.
 * Incluye la configuración básica para generar la documentación interactiva y el esquema de seguridad con JWT para autenticar las solicitudes.
 */
@Slf4j
@Configuration
public class OpenApiConfig {

    /**
     * Configura la documentación OpenAPI para la API de precios, proporcionando información básica sobre la API
     * y la configuración de seguridad utilizando un esquema JWT (JSON Web Token) en el encabezado de autorización.
     * Esta configuración permite la generación de una interfaz Swagger interactiva que describe la API y su funcionamiento.
     *
     * @return Configuración personalizada de OpenAPI para la documentación de la API.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        log.info("Inicializando configuración de OpenAPI para la API de Precios."); // Log para indicar que la configuración está siendo inicializada
        return new OpenAPI()
                .info(new Info()
                        .title("API Pricing") // Título de la API
                        .description("Api para obtener informacion de Producto")) // Descripción de la API
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth")) // Añade el requisito de seguridad con JWT
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme() // Definición del esquema de seguridad JWT
                                .name("Authorization") // Nombre del esquema de seguridad
                                .type(SecurityScheme.Type.HTTP) // Tipo de esquema de seguridad (HTTP)
                                .scheme("bearer") // Tipo de autenticación (Bearer)
                                .bearerFormat("JWT"))); // Formato del token (JWT)
    }
}
