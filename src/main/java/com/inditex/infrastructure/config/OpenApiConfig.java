package com.inditex.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class OpenApiConfig {

    /**
     * Configura la documentación OpenAPI con información básica y esquema de seguridad JWT.
     *
     * @return Configuración personalizada de OpenAPI.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        log.info("Inicializando configuración de OpenAPI.");
        return new OpenAPI()
                .info(new Info()
                        .title("API Pricing")
                        .description("Api para obtener información de Producto"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .name("Authorization")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
