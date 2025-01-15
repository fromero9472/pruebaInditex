package com.inditex.domain.service;

import com.inditex.application.dto.PriceInDTO;
import com.inditex.application.dto.PriceOutDTO;
import com.inditex.application.mapper.PriceMapper;
import com.inditex.domain.exception.PriceNotFoundException;
import com.inditex.domain.exception.PriceServiceException;
import com.inditex.domain.port.in.PriceServicePort;
import com.inditex.domain.port.out.PriceRepositoryPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.inditex.domain.model.Price;

/**
 * Servicio que gestiona la obtención de precios de productos según los parámetros proporcionados.
 * Utiliza un repositorio para buscar los precios y los convierte a un formato adecuado para el cliente.
 * Este servicio maneja excepciones específicas para informar de errores relacionados con la obtención de precios.
 */
@Primary
@Service
@Slf4j
public class PriceService implements PriceServicePort {

    private final PriceMapper priceMapper;

    private final PriceRepositoryPort priceRepositoryPort;

    /**
     * Constructor que inyecta las dependencias necesarias para el servicio de precios.
     *
     * @param priceRepository El repositorio utilizado para acceder a los datos de los precios.
     * @param priceMapper El mapeador para convertir entidades a DTOs.
     */
    public PriceService(@Qualifier("priceRepositoryPort") PriceRepositoryPort priceRepository, PriceMapper priceMapper) {
        this.priceRepositoryPort = priceRepository;
        this.priceMapper = priceMapper;
    }

    /**
     * Obtiene un único precio basado en los parámetros proporcionados: ID de marca, ID de producto y fecha de aplicación.
     * El precio obtenido es el de mayor prioridad. Si no se encuentra el precio, se lanza una excepción {@link PriceNotFoundException}.
     *
     * @param params Parámetros de entrada, que incluyen la marca, producto y fecha de aplicación.
     * @return El precio encontrado convertido a un DTO {@link PriceOutDTO}.
     * @throws PriceServiceException Si ocurre un error inesperado durante la ejecución.
     * @throws PriceNotFoundException Si no se encuentra ningún precio para los parámetros proporcionados.
     * @throws IllegalArgumentException Si los parámetros de entrada son inválidos (null o incorrectos).
     */
    public PriceOutDTO getSinglePrice(PriceInDTO params) {
        try {
            // Extraer parámetros del DTO
            Long brandId = params.getBrandId();
            Long productId = params.getProductId();

            // Validar que los parámetros no sean nulos
            if (brandId == null || productId == null || params.getApplicationDate() == null) {
                throw new IllegalArgumentException("Los parámetros 'brandId', 'productId' y 'applicationDate' son obligatorios.");
            }

            // Parsear la fecha de aplicación a LocalDateTime
            LocalDateTime applicationDate = parseStringToLocalDateTime(params.getApplicationDate());

            log.info("Obteniendo el precio para la Marca ID {}, Producto ID {}, Fecha de Aplicación {}", brandId, productId, applicationDate);

            // Llamada al repositorio para obtener el precio más relevante
            Optional<Price> optionalPrice = priceRepositoryPort.findTopPriceByBrandIdAndProductIdAndApplicationDate(
                    brandId, productId, applicationDate);

            // Manejo del resultado: si no se encuentra, lanzar excepción PriceNotFoundException
            return optionalPrice.map(priceMapper::convertToDTO)
                    .orElseThrow(() -> {
                        log.warn("No se encontraron precios para los parámetros proporcionados");
                        return new PriceNotFoundException("No se encontraron precios para la Marca ID " + brandId + " y Producto ID " + productId);
                    });
        } catch (IllegalArgumentException e) {
            // Capturar y loggear errores de parámetros incorrectos
            log.error("Parámetros inválidos proporcionados para la solicitud de obtener precio", e);
            throw e;  // Propagar la excepción tal cual se recibió
        } catch (PriceNotFoundException e) {
            // Capturar y loggear cuando no se encuentran precios
            log.error("No se encontró el precio para los parámetros proporcionados", e);
            throw e;  // Propagar la excepción personalizada
        } catch (Exception e) {
            // Capturar y loggear errores inesperados, lanzando una excepción personalizada
            log.error("Error inesperado procesando la solicitud de obtener precio", e);
            throw new PriceServiceException("Error inesperado procesando la solicitud de obtener precio", e);  // Excepción personalizada
        }
    }

    /**
     * Convierte una cadena de texto que representa una fecha en formato "yyyy-MM-dd-HH.mm.ss" a un objeto LocalDateTime.
     *
     * @param dateString La cadena de texto que representa la fecha a convertir.
     * @return Un objeto LocalDateTime correspondiente a la cadena proporcionada.
     */
    private LocalDateTime parseStringToLocalDateTime(String dateString) {
        // Definir el formato esperado de la fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");
        // Parsear la fecha
        return LocalDateTime.parse(dateString, formatter);
    }
}
