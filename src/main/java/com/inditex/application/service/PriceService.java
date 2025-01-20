package com.inditex.application.service;

import com.inditex.domain.model.PriceInDTO;
import com.inditex.domain.model.Price;
import com.inditex.application.exception.PriceNotFoundException;
import com.inditex.application.exception.PriceServiceException;
import com.inditex.domain.port.in.GetSinglePrice;
import com.inditex.domain.port.out.PriceRepositoryPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Servicio para obtener precios de productos según marca, producto y fecha.
 */
@Primary
@Service
@Slf4j
public class PriceService implements GetSinglePrice {

    private final PriceRepositoryPort priceRepositoryPort;

    public PriceService(@Qualifier("priceRepositoryPort") PriceRepositoryPort priceRepository) {
        this.priceRepositoryPort = priceRepository;
    }

    /**
     * Obtiene un único precio según marca, producto y fecha.
     *
     * @param params Parámetros con la marca, producto y fecha de aplicación.
     * @return Precio convertido a DTO.
     * @throws PriceServiceException Si ocurre un error inesperado.
     * @throws PriceNotFoundException Si no se encuentra el precio.
     */
    @Override
    public Price getSinglePrice(PriceInDTO params) {
        try {
            // Validación de parámetros
            Long brandId = params.getBrandId();
            Long productId = params.getProductId();
            if (brandId == null || productId == null || params.getApplicationDate() == null) {
                throw new IllegalArgumentException("Parámetros 'brandId', 'productId' y 'applicationDate' son obligatorios.");
            }

            LocalDateTime applicationDate = parseStringToLocalDateTime(params.getApplicationDate());
            log.info("Obteniendo precio para Marca ID {}, Producto ID {}, Fecha {}", brandId, productId, applicationDate);

            // Obtener el precio más relevante desde el repositorio
            Price price = priceRepositoryPort.findTopPriceByBrandIdAndProductIdAndApplicationDate(brandId, productId, applicationDate);

            return price;

        } catch (IllegalArgumentException e) {
            log.error("Parámetros inválidos para obtener precio", e);
            throw e;
        } catch (PriceNotFoundException e) {
            log.error("Precio no encontrado", e);
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al obtener el precio", e);
            throw new PriceServiceException("Error inesperado procesando la solicitud de obtener precio", e);
        }
    }

    /**
     * Convierte una fecha en formato "yyyy-MM-dd-HH.mm.ss" a LocalDateTime.
     *
     * @param dateString Cadena de texto con la fecha.
     * @return LocalDateTime correspondiente.
     */
    private LocalDateTime parseStringToLocalDateTime(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");
        return LocalDateTime.parse(dateString, formatter);
    }
}
