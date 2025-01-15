package com.inditex.application.pojo;
import com.inditex.application.dto.PriceInDTO;
import com.inditex.application.dto.PriceOutDTO;
import com.inditex.domain.model.Price;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
public class PojoTest {

    // --------------- PriceInDTO Tests ---------------

    @Test
    public void testPriceInDTOConstructorAndGettersSetters() {
        Long productId = 35455L;
        Long brandId = 1L;
        String applicationDate = "2020-06-14-00.00.00";

        PriceInDTO dto = new PriceInDTO(productId, brandId, applicationDate);

        assertEquals(productId, dto.getProductId());
        assertEquals(brandId, dto.getBrandId());
        assertEquals(applicationDate, dto.getApplicationDate());
    }

    @Test
    public void testPriceInDTOEmptyConstructor() {
        PriceInDTO dto = new PriceInDTO();
        assertNull(dto.getProductId());
        assertNull(dto.getBrandId());
        assertNull(dto.getApplicationDate());
    }

    @Test
    public void testPriceInDTOSettersAndGetters() {
        PriceInDTO dto = new PriceInDTO();
        dto.setProductId(35455L);
        dto.setBrandId(1L);
        dto.setApplicationDate("2020-06-14-00.00.00");

        assertEquals(35455L, dto.getProductId());
        assertEquals(1L, dto.getBrandId());
        assertEquals("2020-06-14-00.00.00", dto.getApplicationDate());
    }


    @Test
    void testEqualsAndHashCodeDTOIn() {
        // Crear instancias iguales
        PriceInDTO dto1 = new PriceInDTO(1L, 2L, "2023-01-01-10.00.00");
        PriceInDTO dto2 = new PriceInDTO(1L, 2L, "2023-01-01-10.00.00");

        // Verificar que equals funciona correctamente
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        // Crear una instancia diferente
        PriceInDTO dto3 = new PriceInDTO(2L, 3L, "2023-01-01-11.00.00");

        // Verificar que no son iguales
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1.hashCode(), dto3.hashCode());

        // Verificar igualdad reflexiva
        assertEquals(dto1, dto1);

        // Verificar igualdad con nulo
        assertNotEquals(dto1, null);

        // Verificar igualdad con otro tipo de objeto
        assertNotEquals(dto1, "some string");
    }
    @Test
    public void testHashCode() {
        // Crear dos objetos PriceInDTO con los mismos valores
        PriceInDTO priceInDTO1 = new PriceInDTO(1L, 2L, "2025-01-01");
        PriceInDTO priceInDTO2 = new PriceInDTO(1L, 2L, "2025-01-01");

        // Verificar que ambos objetos tienen el mismo hashCode
        assertEquals(priceInDTO1.hashCode(), priceInDTO2.hashCode());

        // Crear un objeto con valores diferentes
        PriceInDTO priceInDTO3 = new PriceInDTO(3L, 4L, "2025-01-02");

        // Verificar que el hashCode es diferente para objetos con valores diferentes
        assertNotEquals(priceInDTO1.hashCode(), priceInDTO3.hashCode());
    }

    @Test
    public void testToStringIn() {
        // Crear un objeto PriceInDTO
        PriceInDTO priceInDTO = new PriceInDTO(1L, 2L, "2025-01-01");

        // Verificar que el método toString no sea null
        assertNotNull(priceInDTO.toString());

        // Verificar que la cadena generada contenga los valores correctos sin comillas alrededor del valor de applicationDate
        assertTrue(priceInDTO.toString().contains("productId=1"));
        assertTrue(priceInDTO.toString().contains("brandId=2"));
        assertTrue(priceInDTO.toString().contains("applicationDate=2025-01-01"));
    }

    @Test
    public void testEmptyConstructor() {
        // Crear un objeto PriceInDTO utilizando el constructor vacío
        PriceInDTO priceInDTO = new PriceInDTO();

        // Verificar que los atributos tengan los valores predeterminados (null para Long y String)
        assertNull(priceInDTO.getProductId());
        assertNull(priceInDTO.getBrandId());
        assertNull(priceInDTO.getApplicationDate());
    }

    // --------------- PriceOutDTO Tests ---------------

    @Test
    public void testPriceOutDTOConstructorAndGettersSetters() {
        Long brandId = 1L;
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 0, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 14, 23, 59, 59, 999999);
        Long priceList = 2L;
        Long productId = 35455L;
        Integer priority = 1;
        Double amount = 10.99;
        String currency = "EUR";

        PriceOutDTO dto = new PriceOutDTO(brandId, startDate, endDate, priceList, productId, priority, amount, currency);

        assertEquals(brandId, dto.getBrandId());
        assertEquals(startDate, dto.getStartDate());
        assertEquals(endDate, dto.getEndDate());
        assertEquals(priceList, dto.getPriceList());
        assertEquals(productId, dto.getProductId());
        assertEquals(priority, dto.getPriority());
        assertEquals(amount, dto.getAmount());
        assertEquals(currency, dto.getCurrency());
    }

    @Test
    public void testPriceOutDTOEmptyConstructor() {
        PriceOutDTO dto = new PriceOutDTO();
        assertNull(dto.getBrandId());
        assertNull(dto.getStartDate());
        assertNull(dto.getEndDate());
        assertNull(dto.getPriceList());
        assertNull(dto.getProductId());
        assertNull(dto.getPriority());
        assertNull(dto.getAmount());
        assertNull(dto.getCurrency());
    }
    @Test
    public void testAllArgsConstructor() {
        // Crear un objeto PriceOutDTO utilizando el constructor @AllArgsConstructor
        Long id = 1L;
        Long brandId = 1L;
        LocalDateTime startDate = LocalDateTime.of(2025, 1, 1, 0, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 1, 2, 0, 0, 0, 0);
        Long priceList = 2L;
        Long productId = 35455L;
        Integer priority = 1;
        Double amount = 100.0;
        String currency = "USD";

        PriceOutDTO priceOutDTO = new PriceOutDTO(id, brandId, startDate, endDate, priceList, productId, priority, amount, currency);

        // Verificar que los valores del objeto coinciden con los valores del constructor
        assertEquals(id, priceOutDTO.getId());
        assertEquals(brandId, priceOutDTO.getBrandId());
        assertEquals(startDate, priceOutDTO.getStartDate());
        assertEquals(endDate, priceOutDTO.getEndDate());
        assertEquals(priceList, priceOutDTO.getPriceList());
        assertEquals(productId, priceOutDTO.getProductId());
        assertEquals(priority, priceOutDTO.getPriority());
        assertEquals(amount, priceOutDTO.getAmount());
        assertEquals(currency, priceOutDTO.getCurrency());
    }
    @Test
    public void testPriceOutDTOGettersAndSetters() {
        // Crear un objeto de la clase PriceOutDTO
        PriceOutDTO priceOutDTO = new PriceOutDTO();

        // Asignar valores utilizando los setters
        priceOutDTO.setId(1L);
        priceOutDTO.setBrandId(2L);
        priceOutDTO.setStartDate(LocalDateTime.of(2021, 1, 1, 0, 0));
        priceOutDTO.setEndDate(LocalDateTime.of(2021, 12, 31, 23, 59));
        priceOutDTO.setPriceList(100L);
        priceOutDTO.setProductId(200L);
        priceOutDTO.setPriority(1);
        priceOutDTO.setAmount(99.99);
        priceOutDTO.setCurrency("USD");

        // Verificar los valores utilizando los getters
        assertEquals(1L, priceOutDTO.getId());
        assertEquals(2L, priceOutDTO.getBrandId());
        assertEquals(LocalDateTime.of(2021, 1, 1, 0, 0), priceOutDTO.getStartDate());
        assertEquals(LocalDateTime.of(2021, 12, 31, 23, 59), priceOutDTO.getEndDate());
        assertEquals(100L, priceOutDTO.getPriceList());
        assertEquals(200L, priceOutDTO.getProductId());
        assertEquals(1, priceOutDTO.getPriority());
        assertEquals(99.99, priceOutDTO.getAmount());
        assertEquals("USD", priceOutDTO.getCurrency());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Crear dos objetos PriceOutDTO con los mismos valores
        PriceOutDTO priceOutDTO1 = new PriceOutDTO(2L, LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 12, 31, 23, 59), 100L, 200L, 1, 99.99, "USD");
        PriceOutDTO priceOutDTO2 = new PriceOutDTO(2L, LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 12, 31, 23, 59), 100L, 200L, 1, 99.99, "USD");

        // Verificar si ambos objetos son iguales
        assertTrue(priceOutDTO1.equals(priceOutDTO2)); // Deben ser iguales
        assertEquals(priceOutDTO1.hashCode(), priceOutDTO2.hashCode()); // Sus hashCodes deben ser iguales

        // Crear un objeto diferente con campos modificados
        PriceOutDTO priceOutDTO3 = new PriceOutDTO(3L, LocalDateTime.of(2022, 1, 1, 0, 0), LocalDateTime.of(2022, 12, 31, 23, 59), 101L, 201L, 2, 199.99, "EUR");

        // Verificar si los objetos son diferentes
        assertFalse(priceOutDTO1.equals(priceOutDTO3)); // No deben ser iguales
        assertNotEquals(priceOutDTO1.hashCode(), priceOutDTO3.hashCode()); // Sus hashCodes deben ser diferentes

        // Test para verificar que un objeto es igual a sí mismo (reflexivo)
        assertTrue(priceOutDTO1.equals(priceOutDTO1)); // Debe ser verdadero

        // Test para verificar que los objetos no son iguales a null
        assertFalse(priceOutDTO1.equals(null)); // No debe ser igual a null

        // Verificar que los hashCodes sean consistentes
        assertEquals(priceOutDTO1.hashCode(), priceOutDTO1.hashCode()); // El hashCode debe ser consistente para el mismo objeto
        assertEquals(priceOutDTO2.hashCode(), priceOutDTO2.hashCode()); // El hashCode debe ser consistente para el mismo objeto

        // Test para comparar objetos con solo algunos campos diferentes
        PriceOutDTO priceOutDTO4 = new PriceOutDTO(2L, LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 12, 31, 23, 59), 100L, 200L, 1, 99.99, "USD");
        PriceOutDTO priceOutDTO5 = new PriceOutDTO(2L, LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 12, 31, 23, 59), 100L, 200L, 1, 99.99, "USD");

        // Verificar que dos objetos con los mismos valores son iguales
        assertTrue(priceOutDTO4.equals(priceOutDTO5)); // Deben ser iguales
        assertEquals(priceOutDTO4.hashCode(), priceOutDTO5.hashCode()); // Sus hashCodes deben ser iguales

        // Verificar que objetos con diferencias en un campo no sean iguales
        PriceOutDTO priceOutDTO6 = new PriceOutDTO(2L, LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 12, 31, 23, 59), 100L, 200L, 1, 99.99, "EUR");
        assertFalse(priceOutDTO4.equals(priceOutDTO6)); // Diferente valor para currency
        assertNotEquals(priceOutDTO4.hashCode(), priceOutDTO6.hashCode()); // hashCode debe ser diferente

        // Test: PriceOutDTO con un campo null
        PriceOutDTO priceOutDTO7 = new PriceOutDTO(null, LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 12, 31, 23, 59), 100L, 200L, 1, 99.99, "USD");
        PriceOutDTO priceOutDTO8 = new PriceOutDTO(null, LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 12, 31, 23, 59), 100L, 200L, 1, 99.99, "USD");

        // Verificar que objetos con un campo null sean iguales
        assertTrue(priceOutDTO7.equals(priceOutDTO8)); // Ambos objetos tienen valores nulos
        assertEquals(priceOutDTO7.hashCode(), priceOutDTO8.hashCode()); // Sus hashCodes deben ser iguales

        // Test para comparar PriceOutDTO con diferentes clases (debe retornar false)
        Object otherObject = new Object();
        assertFalse(priceOutDTO1.equals(otherObject)); // Diferente clase, no debe ser igual

        // Test para comparar PriceOutDTO con valores nulos en algunos campos
        PriceOutDTO priceOutDTO9 = new PriceOutDTO(null, null, null, null, null, null, null, null);
        PriceOutDTO priceOutDTO10 = new PriceOutDTO(null, null, null, null, null, null, null, null);

        assertTrue(priceOutDTO9.equals(priceOutDTO10)); // Ambos objetos tienen valores nulos
        assertEquals(priceOutDTO9.hashCode(), priceOutDTO10.hashCode()); // Sus hashCodes deben ser iguales

        // Test para comparar objetos donde un campo es null pero el resto es igual
        PriceOutDTO priceOutDTO11 = new PriceOutDTO(2L, null, LocalDateTime.of(2021, 12, 31, 23, 59), 100L, 200L, 1, 99.99, "USD");
        PriceOutDTO priceOutDTO12 = new PriceOutDTO(2L, null, LocalDateTime.of(2021, 12, 31, 23, 59), 100L, 200L, 1, 99.99, "USD");

        assertTrue(priceOutDTO11.equals(priceOutDTO12)); // Ambos objetos son iguales a pesar del campo null
        assertEquals(priceOutDTO11.hashCode(), priceOutDTO12.hashCode()); // Sus hashCodes deben ser iguales

        // Verificar igualdad de objetos con valores diferentes para todos los campos
        PriceOutDTO priceOutDTO13 = new PriceOutDTO(3L, LocalDateTime.of(2022, 1, 1, 0, 0), LocalDateTime.of(2022, 12, 31, 23, 59), 101L, 201L, 2, 199.99, "EUR");
        PriceOutDTO priceOutDTO14 = new PriceOutDTO(4L, LocalDateTime.of(2023, 2, 1, 0, 0), LocalDateTime.of(2023, 12, 31, 23, 59), 102L, 202L, 3, 299.99, "GBP");

        assertFalse(priceOutDTO13.equals(priceOutDTO14)); // Deberían ser diferentes
        assertNotEquals(priceOutDTO13.hashCode(), priceOutDTO14.hashCode()); // Sus hashCodes deberían ser diferentes
    }
    @Test
    public void testToString() {
        // Crear un objeto PriceOutDTO
        PriceOutDTO priceOutDTO = new PriceOutDTO(2L, LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 12, 31, 23, 59), 100L, 200L, 1, 99.99, "USD");

        // Verificar que el método toString no sea null
        assertNotNull(priceOutDTO.toString());
        // Puedes agregar más pruebas para asegurarte de que la salida contiene las partes correctas
        assertTrue(priceOutDTO.toString().contains("brandId=2"));
        assertTrue(priceOutDTO.toString().contains("startDate=2021-01-01T00:00"));
    }
    // --------------- Price Entity Tests ---------------
    @Test
    public void testPriceConstructorAndGettersSetters() {
        Long brandId = 1L;
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 0, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 14, 23, 59, 59, 999999);
        Long priceList = 2L;
        Long productId = 35455L;
        Integer priority = 1;
        Double amount = 10.99;
        String currency = "EUR";

        Price price = new Price(brandId, startDate, endDate, priceList, productId, priority, amount, currency);

        assertEquals(brandId, price.getBrandId());
        assertEquals(startDate, price.getStartDate());
        assertEquals(endDate, price.getEndDate());
        assertEquals(priceList, price.getPriceList());
        assertEquals(productId, price.getProductId());
        assertEquals(priority, price.getPriority());
        assertEquals(amount, price.getAmount());
        assertEquals(currency, price.getCurrency());
    }

    @Test
    public void testSetStartDate() {
        Price price = new Price();
        LocalDateTime startDate = LocalDateTime.of(2025, 1, 1, 0, 0, 0, 0);
        price.setStartDate(startDate);
        assertEquals(startDate, price.getStartDate());
    }

    @Test
    public void testSetProductId() {
        Price price = new Price();
        Long productId = 123L;
        price.setProductId(productId);
        assertEquals(Long.valueOf(123), price.getProductId());
    }

    @Test
    public void testSetPriority() {
        Price price = new Price();
        Integer priority = 5;
        price.setPriority(priority);
        assertEquals(Integer.valueOf(5), price.getPriority());
    }

    @Test
    public void testSetPriceList() {
        Price price = new Price();
        Long priceList = 999L;
        price.setPriceList(priceList);
        assertEquals(Long.valueOf(999), price.getPriceList());
    }

    @Test
    public void testSetEndDate() {
        Price price = new Price();
        LocalDateTime endDate = LocalDateTime.of(2025, 1, 2, 0, 0, 0, 0);
        price.setEndDate(endDate);
        assertEquals(endDate, price.getEndDate());
    }

    @Test
    public void testSetBrandId() {
        Price price = new Price();
        Long brandId = 456L;
        price.setBrandId(brandId);
        assertEquals(Long.valueOf(456), price.getBrandId());
    }

    @Test
    public void testSetAmount() {
        Price price = new Price();
        Double amount = 100.5;
        price.setAmount(amount);
        assertEquals(Double.valueOf(100.5), price.getAmount());
    }

    @Test
    public void testEquals() {
        Price price1 = new Price(1L, 1L, LocalDateTime.of(2025, 1, 1, 0, 0, 0, 0), LocalDateTime.of(2025, 1, 2, 0, 0, 0, 0), 1L, 1L, 1, 100.0, "USD");
        Price price2 = new Price(1L, 1L, LocalDateTime.of(2025, 1, 1, 0, 0, 0, 0), LocalDateTime.of(2025, 1, 2, 0, 0, 0, 0), 1L, 1L, 1, 100.0, "USD");
        Price price3 = new Price(2L, 1L, LocalDateTime.of(2025, 1, 1, 0, 0, 0, 0), LocalDateTime.of(2025, 1, 2, 0, 0, 0, 0), 1L, 1L, 1, 100.0, "USD");

        assertTrue(price1.equals(price2)); // Deben ser iguales
        assertFalse(price1.equals(price3)); // Deben ser diferentes
        assertFalse(price1.equals(null)); // No deben ser iguales a null
    }

    @Test
    public void testToStringEntity() {
        Price price = new Price(1L, 1L, LocalDateTime.of(2025, 1, 1, 0, 0, 0, 0), LocalDateTime.of(2025, 1, 2, 0, 0, 0, 0), 1L, 1L, 1, 100.0, "USD");

        assertNotNull(price.toString());

        assertTrue(price.toString().contains("id=1"));
        assertTrue(price.toString().contains("brandId=1"));
        assertTrue(price.toString().contains("startDate=2025-01-01T00:00"));
        assertTrue(price.toString().contains("endDate=2025-01-02T00:00"));
        assertTrue(price.toString().contains("priceList=1"));
        assertTrue(price.toString().contains("productId=1"));
        assertTrue(price.toString().contains("priority=1"));
        assertTrue(price.toString().contains("amount=100.0"));
        assertTrue(price.toString().contains("currency=USD"));
    }

    @Test
    public void testHashCodeEntity() {
        Price price1 = new Price(1L, 1L, LocalDateTime.of(2025, 1, 1, 0, 0, 0, 0), LocalDateTime.of(2025, 1, 2, 0, 0, 0, 0), 1L, 1L, 1, 100.0, "USD");
        Price price2 = new Price(1L, 1L, LocalDateTime.of(2025, 1, 1, 0, 0, 0, 0), LocalDateTime.of(2025, 1, 2, 0, 0, 0, 0), 1L, 1L, 1, 100.0, "USD");

        assertEquals(price1.hashCode(), price2.hashCode());

        price2.setCurrency("EUR");

        assertNotEquals(price1.hashCode(), price2.hashCode());
    }

    @Test
    public void testEntityConstructor() {
        Price price = new Price(1L, 1L, LocalDateTime.of(2025, 1, 1, 0, 0, 0, 0), LocalDateTime.of(2025, 1, 2, 0, 0, 0, 0), 1L, 1L, 1, 100.0, "USD");

        assertEquals(1L, price.getBrandId());
        assertEquals(LocalDateTime.of(2025, 1, 1, 0, 0, 0, 0), price.getStartDate());
        assertEquals(LocalDateTime.of(2025, 1, 2, 0, 0, 0, 0), price.getEndDate());
        assertEquals(1L, price.getPriceList());
        assertEquals(1L, price.getProductId());
        assertEquals(1, price.getPriority());
        assertEquals(100.0, price.getAmount());
        assertEquals("USD", price.getCurrency());
    }

    @Test
    public void testPriceEntityProperties() {
        Price price = new Price(1L, LocalDateTime.of(2020, 6, 14, 0, 0, 0, 0), LocalDateTime.of(2020, 6, 14, 23, 59, 59, 999999), 2L, 35455L, 1, 10.99, "EUR");
        price.setId(1L);  // Asignar un ID manualmente para que la prueba pase

        assertNotNull(price.getId());
        assertEquals(35455L, price.getProductId());
        assertEquals(1L, price.getBrandId());
        assertEquals(10.99, price.getAmount());
    }

    @Test
    public void testPriceEntityWithoutId() {
        Price price = new Price();
        assertNull(price.getId());
    }

}
