package com.inditex.infrastructure.config;

import com.inditex.application.mapper.PriceMapper;
import com.inditex.domain.port.in.AuthServicePort;
import com.inditex.domain.port.in.PriceServicePort;
import com.inditex.domain.port.out.PriceRepositoryPort;
import com.inditex.domain.service.AuthService;
import com.inditex.domain.service.PriceService;
import com.inditex.infrastructure.repository.PriceRepository;
import com.inditex.infrastructure.repository.PriceRepositoryAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j  // Lombok genera automáticamente el logger
@Configuration
public class AppConfig {

    /**
     * Define el bean para el repositorio de precios.
     * Utiliza un adaptador que adapta el repositorio específico a la interfaz que se espera por el dominio.
     *
     * @param priceRepository El repositorio de precios, proporcionado por Spring.
     * @return Un adaptador para el repositorio de precios, implementando la interfaz PriceRepositoryPort.
     */
    @Bean
    public PriceRepositoryPort priceRepositoryPort(PriceRepository priceRepository) {
        log.info("Creando el bean PriceRepositoryPort");
        // Retorna un adaptador que convierte la implementación del repositorio en un puerto adecuado para la capa de dominio.
        return new PriceRepositoryAdapter(priceRepository);
    }

    /**
     * Define el bean para el servicio de precios.
     * Este servicio usa el repositorio de precios y un mapper para manejar la lógica de negocio relacionada con los precios.
     *
     * @param priceRepositoryPort El puerto para interactuar con el repositorio de precios.
     * @param priceMapper El mapper utilizado para convertir entre las entidades y los DTOs.
     * @return Un servicio que gestiona la obtención de precios.
     */
    @Bean
    public PriceServicePort priceServicePort(PriceRepositoryPort priceRepositoryPort, PriceMapper priceMapper) {
        log.info("Creando el bean PriceServicePort");
        // Retorna una instancia de PriceService, que es un servicio de aplicación que maneja la obtención de precios.
        return new PriceService(priceRepositoryPort, priceMapper);
    }

    /**
     * Define el bean para el servicio de autenticación.
     * El servicio de autenticación gestiona la validación de credenciales de usuarios.
     *
     * @return Un servicio que valida las credenciales del usuario.
     */
    @Bean
    public AuthServicePort authServicePort() {
        log.info("Creando el bean AuthServicePort");
        // Retorna una instancia de AuthService, que es el servicio responsable de validar las credenciales de usuario.
        return new AuthService();
    }
}
