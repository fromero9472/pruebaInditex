package com.inditex.infrastructure.config;

import com.inditex.domain.model.Price;
import com.inditex.infrastructure.repository.PriceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase de configuración que carga datos iniciales en la base de datos.
 * Esta clase se ejecuta al inicio de la aplicación para cargar un conjunto de datos de ejemplo relacionados con precios.
 * Utiliza la anotación @PostConstruct para ejecutar el método loadData después de que los beans hayan sido inicializados.
 */
@Configuration
@Slf4j
public class DataLoadConfig {

    @Autowired
    private PriceRepository priceRepository;

    /**
     * Método que se ejecuta después de la construcción del bean y carga datos de ejemplo en la base de datos.
     * Los datos cargados son ejemplos de precios, con diferentes fechas de inicio y finalización.
     *
     * @throws ParseException Si ocurre un error al parsear las fechas de los precios.
     */
    @PostConstruct
    public void loadData() throws ParseException {
        // Formato de fecha para las fechas de inicio y finalización
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");

        log.info("Cargando datos iniciales en la base de datos...");
        // Instanciamos los objetos Price
        Price price1 = new Price(
                1L,
                LocalDateTime.parse("2020-06-14T00:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59"),
                1L,
                35455L,
                0,
                35.50,
                "EUR");

        Price price2 = new Price(
                1L,
                LocalDateTime.parse("2020-06-14T15:00:00"),
                LocalDateTime.parse("2020-06-14T18:30:00"),
                2L,
                35455L,
                1,
                25.45,
                "EUR");

        Price price3 = new Price(
                1L,
                LocalDateTime.parse("2020-06-15T00:00:00"),
                LocalDateTime.parse("2020-06-15T11:00:00"),
                3L,
                35455L,
                1,
                30.50,
                "EUR");

        Price price4 = new Price(
                1L,
                LocalDateTime.parse("2020-06-15T16:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59"),
                4L,
                35455L,
                1,
                38.95,
                "EUR");

        // Guardamos las instancias en la base de datos usando el repositorio
        priceRepository.save(price1);
        priceRepository.save(price2);
        priceRepository.save(price3);
        priceRepository.save(price4);

        log.info("Datos iniciales cargados correctamente.");
    }

    /**
     * Método setter para permitir la inyección de dependencias en pruebas.
     * Este setter se utiliza principalmente para sustituir el repositorio real en un entorno de prueba.
     *
     * @param priceRepository El repositorio de precios que se inyectará en las pruebas.
     */
    public void setPriceRepository(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }
}
