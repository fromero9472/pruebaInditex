package com.inditex.application.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class PriceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private String authToken;

    @BeforeEach
    public void setup() throws Exception {
        // Obtener el token de autenticación (real, desde el endpoint)
        String loginUrl = "/v1/auth/generate-token";
        String loginResponse = mockMvc.perform(MockMvcRequestBuilders.post(loginUrl)
                        .param("user", "usuario")
                        .param("contrasena", "password")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        authToken = loginResponse;
    }

    @Test
    public void testGetPriceList_Success() throws Exception {
        // Configuración de parámetros
        Long brandId = 1L;
        Long productId = 35455L;
        String applicationDate = "2020-06-14-00.00.00";

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/prices/getPrice")
                        .param("brandId", String.valueOf(brandId))
                        .param("productId", String.valueOf(productId))
                        .param("applicationDate", applicationDate)
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())  // Esperamos un código HTTP 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(productId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value(brandId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2020-06-14T00:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2020-12-31T23:59:59"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceList").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(productId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priority").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(38.5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currency").value("EUR")); // Valor de la moneda esperado
    }

}
