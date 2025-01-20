package com.inditex.application.mapper;
import com.inditex.domain.model.Price;
import com.inditex.infrastructure.entity.PriceEntity;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;


class PriceMapperTest {

    @Mock
    private ModelMapper modelMapper; // Mock de ModelMapper

    @InjectMocks
    private PriceMapper priceMapper; // PriceMapper con el ModelMapper mockeado

    @BeforeEach
    public void setUp() {
        // Inicializa los mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConvertToDTO() {
        // Crear un objeto Price para la prueba
        PriceEntity price = new PriceEntity(1L, 1L, LocalDateTime.of(2025, 1, 1, 0, 0, 0, 0),
                LocalDateTime.of(2025, 1, 2, 0, 0, 0, 0), 1L, 1L, 1, 100.0, "USD");

        // Crear el PriceOutDTO esperado
        Price priceOutDTO = new Price(1L, 1L, LocalDateTime.of(2025, 1, 1, 0, 0, 0, 0),
                LocalDateTime.of(2025, 1, 2, 0, 0, 0, 0), 1L, 1L, 1, 100.0, "USD");

        // Configurar el comportamiento del mock de ModelMapper
        when(modelMapper.map(price, Price.class)).thenReturn(priceOutDTO);

        // Ejecutar la conversión
        Price result = priceMapper.convertToDTO(price);

        // Verificar que el resultado no sea nulo y sea igual al esperado
        assertNotNull(result);
        assertEquals(priceOutDTO, result);
    }

    @Test
    public void testConvertToEntity() {
        // Crear un objeto PriceOutDTO para la prueba
        Price priceOutDTO = new Price(1L, 1L, LocalDateTime.of(2025, 1, 1, 0, 0, 0, 0),
                LocalDateTime.of(2025, 1, 2, 0, 0, 0, 0), 1L, 1L, 1, 100.0, "USD");

        // Crear el Price esperado
        PriceEntity price = new PriceEntity(1L, 1L, LocalDateTime.of(2025, 1, 1, 0, 0, 0, 0),
                LocalDateTime.of(2025, 1, 2, 0, 0, 0, 0), 1L, 1L, 1, 100.0, "USD");

        // Configurar el comportamiento del mock de ModelMapper
        when(modelMapper.map(priceOutDTO, PriceEntity.class)).thenReturn(price);

        // Ejecutar la conversión
        PriceEntity result = priceMapper.convertToEntity(priceOutDTO);

        // Verificar que el resultado no sea nulo y sea igual al esperado
        assertNotNull(result);
        assertEquals(price, result);
    }
}
