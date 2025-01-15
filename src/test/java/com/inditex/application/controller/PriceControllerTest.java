package com.inditex.application.controller;

import com.inditex.application.dto.PriceInDTO;
import com.inditex.application.dto.PriceOutDTO;
import com.inditex.domain.port.in.PriceServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PriceControllerTest {

    @Mock
    private PriceServicePort priceService;

    @InjectMocks
    private PriceController priceController;

    private PriceInDTO params;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Inicializar los parámetros de prueba
        params = new PriceInDTO();
        params.setProductId(35455L);
        params.setBrandId(2L);
        params.setApplicationDate("2020-06-14-15.00.00");
    }

    @Test
    void testGetPriceList_Success() {
        // Crear un DTO de respuesta esperado
        PriceOutDTO expectedPriceOutDTO = new PriceOutDTO();

        expectedPriceOutDTO.setProductId(35455L);
        expectedPriceOutDTO.setBrandId(2L);
        expectedPriceOutDTO.setAmount(100.50);

        // Mockear el comportamiento del servicio
        when(priceService.getSinglePrice(params)).thenReturn(expectedPriceOutDTO);

        // Llamar al método del controlador
        ResponseEntity<PriceOutDTO> response = priceController.getPriceList(params.getBrandId(),params.getProductId(),params.getApplicationDate());

        // Verificar que la respuesta es correcta
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedPriceOutDTO, response.getBody());
    }


}
