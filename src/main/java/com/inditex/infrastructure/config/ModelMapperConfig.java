package com.inditex.infrastructure.config;

import org.modelmapper.ModelMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración para ModelMapper, utilizado para la conversión de objetos entre diferentes tipos de datos.
 * ModelMapper facilita el mapeo entre entidades de la capa de dominio y los objetos de transferencia de datos (DTOs),
 * simplificando la transformación entre estos tipos de objetos.
 */
@Slf4j
@Configuration
public class ModelMapperConfig {

    /**
     * Define un bean de tipo ModelMapper para la inyección de dependencias en otras partes de la aplicación.
     * Este bean será utilizado en los servicios y controladores para mapear datos entre las diferentes capas
     * de la aplicación, como la capa de dominio y la capa de presentación (DTOs).
     *
     * @return Una instancia de ModelMapper, que se puede usar para realizar conversiones entre tipos de objetos.
     */
    @Bean
    public ModelMapper modelMapper() {
        log.info("Inicializando ModelMapper para la conversión de objetos."); // Log de información al crear el bean
        return new ModelMapper(); // Se crea y devuelve una instancia de ModelMapper
    }
}
