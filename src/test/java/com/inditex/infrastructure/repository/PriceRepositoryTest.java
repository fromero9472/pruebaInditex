package com.inditex.infrastructure.repository;

import com.inditex.domain.model.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)  // Habilitar soporte para Mockito
class PriceRepositoryTest {

    @Mock
    private PriceRepository priceRepository; // Mock del PriceRepository

    @InjectMocks
    private PriceRepositoryAdapter priceRepositoryAdapter; // PriceRepositoryAdapter que usa el mock de PriceRepository

    private Long brandId;
    private Long productId;
    private LocalDateTime applicationDate;

    @BeforeEach
    public void setUp() {
        // Inicializar los valores comunes para las pruebas
        brandId = 1L;
        productId = 35455L;
        applicationDate = LocalDateTime.of(2025, 1, 1, 0, 0, 0, 0);
    }
    @Test
    public void testFindTopPriceByBrandIdAndProductIdAndApplicationDate_PriceFound() {
        // Crear un objeto Price simulado que se devolverá envuelto en un Optional
        Price mockPrice = new Price(1L, brandId, applicationDate, applicationDate.plusDays(1), 1L, productId, 1, 100.0, "USD");
        Optional<Price> mockOptionalPrice = Optional.of(mockPrice);

        // Configurar el mock para devolver el Optional con el precio simulado cuando se invoque el método por defecto
        when(priceRepository.findTopByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(brandId, productId, applicationDate,applicationDate))
                .thenReturn(mockOptionalPrice);

        // Llamar al método del adaptador
        Price result = priceRepositoryAdapter.findTopPriceByBrandIdAndProductIdAndApplicationDate(brandId, productId, applicationDate);

        // Verificar que el resultado esté presente y coincida con el precio simulado

        assertEquals(mockPrice, result);

        // Verificar que el método findTopPriceByBrandIdAndProductIdAndApplicationDateDefault haya sido llamado
        verify(priceRepository, times(1))
                .findTopByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(brandId, productId, applicationDate,applicationDate);

        // Verificar que no se haya llamado al método findTopPricesByBrandIdAndProductIdAndApplicationDate
        verify(priceRepository, times(0))
                .findTopByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(brandId, productId, applicationDate,applicationDate);
    }



    @Test
    public void testFindTopPriceByBrandIdAndProductIdAndApplicationDate_NoPriceFound() {
        // Configurar el mock para devolver un Optional vacío cuando no haya resultados
        when(priceRepository.findTopByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(brandId, productId, applicationDate,applicationDate))
                .thenReturn(Optional.empty());

        // Llamar al método del adaptador
       Price result = priceRepositoryAdapter.findTopPriceByBrandIdAndProductIdAndApplicationDate(brandId, productId, applicationDate);

        // Verificar que el resultado esté vacío

        // Verificar que el método findTopPriceByBrandIdAndProductIdAndApplicationDateDefault haya sido llamado
        verify(priceRepository, times(1))
                .findTopByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(brandId, productId, applicationDate,applicationDate);

        // Verificar que no se haya llamado al método findTopPricesByBrandIdAndProductIdAndApplicationDate
        verify(priceRepository, times(0))
                .findTopByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(brandId, productId, applicationDate,applicationDate);
    }


}
