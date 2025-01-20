package com.inditex.application.controller;

import com.inditex.domain.model.PriceInDTO;
import com.inditex.domain.model.Price;
import com.inditex.domain.port.in.GetSinglePrice;
import com.inditex.infrastructure.controller.PriceController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PriceControllerTest {

    @Mock
    private GetSinglePrice priceService;

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
        Price expectedPriceOutDTO = new Price();

        expectedPriceOutDTO.setProductId(35455L);
        expectedPriceOutDTO.setBrandId(2L);
        expectedPriceOutDTO.setAmount(100.50);

        // Mockear el comportamiento del servicio
        when(priceService.getSinglePrice(params)).thenReturn(expectedPriceOutDTO);

        // Llamar al método del controlador
        ResponseEntity<Price> response = priceController.getPriceList(params.getBrandId(),params.getProductId(),params.getApplicationDate());

        // Verificar que la respuesta es correcta
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedPriceOutDTO, response.getBody());
    }


}
