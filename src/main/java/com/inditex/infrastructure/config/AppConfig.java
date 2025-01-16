package com.inditex.infrastructure.config;

import com.inditex.application.mapper.PriceMapper;
import com.inditex.domain.port.in.AuthServicePort;
import com.inditex.domain.port.in.PriceServicePort;
import com.inditex.domain.port.out.CredentialValidator;
import com.inditex.domain.port.out.PriceRepositoryPort;
import com.inditex.domain.service.AuthService;
import com.inditex.domain.service.PriceService;
import com.inditex.infrastructure.repository.PriceRepository;
import com.inditex.infrastructure.repository.PriceRepositoryAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AppConfig {

    /**
     * Define el bean para el repositorio de precios.
     * Utiliza un adaptador para adaptar el repositorio a la interfaz esperada.
     *
     * @param priceRepository El repositorio de precios.
     * @return Un adaptador para el repositorio de precios.
     */
    @Bean
    public PriceRepositoryPort priceRepositoryPort(PriceRepository priceRepository) {
        log.info("Creando el bean PriceRepositoryPort");
        return new PriceRepositoryAdapter(priceRepository);
    }

    /**
     * Define el bean para el servicio de precios.
     * Gestiona la obtención de precios utilizando el repositorio y un mapper.
     *
     * @param priceRepositoryPort El puerto para interactuar con el repositorio.
     * @param priceMapper El mapper para convertir entre entidades y DTOs.
     * @return Un servicio que maneja la obtención de precios.
     */
    @Bean
    public PriceServicePort priceServicePort(PriceRepositoryPort priceRepositoryPort, PriceMapper priceMapper) {
        log.info("Creando el bean PriceServicePort");
        return new PriceService(priceRepositoryPort, priceMapper);
    }

    /**
     * Define el bean para el servicio de autenticación.
     * Valida las credenciales de usuario.
     *
     * @param credentialValidator Componente encargado de la validación de credenciales.
     * @return Un servicio que valida las credenciales del usuario.
     */
    @Bean
    public AuthServicePort authServicePort(CredentialValidator credentialValidator) {
        log.info("Creando el bean AuthServicePort");
        return new AuthService(credentialValidator);
    }
}
