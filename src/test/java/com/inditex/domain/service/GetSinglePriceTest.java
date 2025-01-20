package com.inditex.domain.service;

import com.inditex.application.service.PriceService;
import com.inditex.domain.model.PriceInDTO;
import com.inditex.domain.model.Price;
import com.inditex.application.mapper.PriceMapper;
import com.inditex.application.exception.PriceNotFoundException;
import com.inditex.application.exception.PriceServiceException;
import com.inditex.domain.port.out.PriceRepositoryPort;
import com.inditex.infrastructure.entity.PriceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration
class GetSinglePriceTest {

    @Mock
    private PriceRepositoryPort priceRepository;

    @Mock
    private PriceMapper priceMapper;

    @InjectMocks
    private PriceService priceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }

    @Test
    void testGetSinglePrice_At_10AM_14June() {
        // Arrange
        Long brandId = 1L;
        Long productId = 35455L;
        String dateTime = "2020-06-14-10.00.00";

        Price mockPrice = new Price(1L,
                parseStringToLocalDateTime("2020-06-14-00.00.00"),
                parseStringToLocalDateTime("2020-12-31-23.59.59"),
                brandId, productId, 1, 38.50, "EUR");

        when(priceRepository.findTopPriceByBrandIdAndProductIdAndApplicationDate(
                eq(brandId), eq(productId), eq(parseStringToLocalDateTime(dateTime))))
                .thenReturn(mockPrice);

        // Act
        PriceInDTO params = new PriceInDTO(productId, brandId, dateTime);
        Price result = priceService.getSinglePrice(params);

        // Assert
        verify(priceRepository, times(1)).findTopPriceByBrandIdAndProductIdAndApplicationDate(brandId, productId, parseStringToLocalDateTime(dateTime));
    }

    @Test
    void testGetSinglePrice_NoPriceFound() {
        // Arrange
        Long brandId = 1L;
        Long productId = 35455L;
        String dateTime = "2023-12-14-10.00.00";
        LocalDateTime applicationDate = parseStringToLocalDateTime(dateTime);

        // Simula que el repositorio lanza la excepción PriceNotFoundException
        when(priceRepository.findTopPriceByBrandIdAndProductIdAndApplicationDate(brandId, productId, applicationDate))
                .thenThrow(new PriceNotFoundException("No se encontraron precios para la Marca ID " + brandId + " y Producto ID " + productId));

        PriceInDTO params = new PriceInDTO(productId, brandId, dateTime);

        // Act and Assert
        PriceNotFoundException exception = assertThrows(
                PriceNotFoundException.class,
                () -> priceService.getSinglePrice(params)
        );

        assertEquals("No se encontraron precios para la Marca ID " + brandId + " y Producto ID " + productId, exception.getMessage());
    }


    @Test
    void testGetSinglePrice_ExceptionHandling() {
        // Arrange
        Long brandId = 1L;
        Long productId = 35455L;
        String dateTime = "2023-12-14-10.00.00";

        when(priceRepository.findTopPriceByBrandIdAndProductIdAndApplicationDate(brandId, productId, parseStringToLocalDateTime(dateTime)))
                .thenThrow(new RuntimeException("Error in repository"));

        PriceInDTO params = new PriceInDTO(productId, brandId, dateTime);

        // Act and Assert
        PriceServiceException exception = assertThrows(PriceServiceException.class, () -> priceService.getSinglePrice(params));
        assertEquals("Error inesperado procesando la solicitud de obtener precio", exception.getMessage());
    }

    @Test
    void testGetSinglePrice_InvalidParameter() {
        // Arrange
        PriceInDTO params = new PriceInDTO();
        params.setBrandId(1L);
        params.setProductId(35455L);
        params.setApplicationDate(null);

        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> priceService.getSinglePrice(params));
        assertEquals("Parámetros 'brandId', 'productId' y 'applicationDate' son obligatorios.", exception.getMessage());
    }

    @Test
    void testGetSinglePrice_MissingParameter() {
        // Arrange
        PriceInDTO params = new PriceInDTO();
        params.setBrandId(1L);
        params.setApplicationDate("2023-12-14-10.00.00");

        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> priceService.getSinglePrice(params));
        assertEquals("Parámetros 'brandId', 'productId' y 'applicationDate' son obligatorios.", exception.getMessage());
    }

    // Helper method to parse strings into LocalDateTime
    private LocalDateTime parseStringToLocalDateTime(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");
        return LocalDateTime.parse(dateString, formatter);
    }
}
