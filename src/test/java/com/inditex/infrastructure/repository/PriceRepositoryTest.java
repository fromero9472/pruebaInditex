package com.inditex.infrastructure.repository;

import com.inditex.domain.model.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
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
    private LocalDateTime startDate;

    @BeforeEach
    public void setUp() {
        // Inicializar los valores comunes para las pruebas
        brandId = 1L;
        productId = 35455L;
        startDate = LocalDateTime.of(2025, 1, 1, 0, 0, 0, 0);
    }
    @Test
    public void testFindTopPriceByBrandIdAndProductIdAndApplicationDate_PriceFound() {
        // Crear un objeto Price simulado que se devolverá envuelto en un Optional
        Price mockPrice = new Price(1L, brandId, startDate, startDate.plusDays(1), 1L, productId, 1, 100.0, "USD");
        Optional<Price> mockOptionalPrice = Optional.of(mockPrice);

        // Configurar el mock para devolver el Optional con el precio simulado cuando se invoque el método por defecto
        when(priceRepository.findTopPriceByBrandIdAndProductIdAndApplicationDateDefault(brandId, productId, startDate))
                .thenReturn(mockOptionalPrice);

        // Llamar al método del adaptador
        Optional<Price> result = priceRepositoryAdapter.findTopPriceByBrandIdAndProductIdAndApplicationDate(brandId, productId, startDate);

        // Verificar que el resultado esté presente y coincida con el precio simulado
        assertTrue(result.isPresent());
        assertEquals(mockPrice, result.get());

        // Verificar que el método findTopPriceByBrandIdAndProductIdAndApplicationDateDefault haya sido llamado
        verify(priceRepository, times(1))
                .findTopPriceByBrandIdAndProductIdAndApplicationDateDefault(brandId, productId, startDate);

        // Verificar que no se haya llamado al método findTopPricesByBrandIdAndProductIdAndApplicationDate
        verify(priceRepository, times(0))
                .findTopPricesByBrandIdAndProductIdAndApplicationDate(brandId, productId, startDate);
    }



    @Test
    public void testFindTopPriceByBrandIdAndProductIdAndApplicationDate_NoPriceFound() {
        // Configurar el mock para devolver un Optional vacío cuando no haya resultados
        when(priceRepository.findTopPriceByBrandIdAndProductIdAndApplicationDateDefault(brandId, productId, startDate))
                .thenReturn(Optional.empty());

        // Llamar al método del adaptador
        Optional<Price> result = priceRepositoryAdapter.findTopPriceByBrandIdAndProductIdAndApplicationDate(brandId, productId, startDate);

        // Verificar que el resultado esté vacío
        assertTrue(result.isEmpty());

        // Verificar que el método findTopPriceByBrandIdAndProductIdAndApplicationDateDefault haya sido llamado
        verify(priceRepository, times(1))
                .findTopPriceByBrandIdAndProductIdAndApplicationDateDefault(brandId, productId, startDate);

        // Verificar que no se haya llamado al método findTopPricesByBrandIdAndProductIdAndApplicationDate
        verify(priceRepository, times(0))
                .findTopPricesByBrandIdAndProductIdAndApplicationDate(brandId, productId, startDate);
    }


}
