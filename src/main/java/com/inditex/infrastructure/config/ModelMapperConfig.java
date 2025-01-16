package com.inditex.infrastructure.config;

import org.modelmapper.ModelMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ModelMapperConfig {

    /**
     * Define un bean de ModelMapper para convertir objetos entre diferentes tipos.
     * Facilita el mapeo entre entidades y DTOs.
     *
     * @return Una instancia de ModelMapper.
     */
    @Bean
    public ModelMapper modelMapper() {
        log.info("Inicializando ModelMapper."); // Log al crear el bean
        return new ModelMapper(); // Retorna la instancia de ModelMapper
    }
}
