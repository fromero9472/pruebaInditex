package com.inditex.infrastructure.repository;

import com.inditex.domain.model.Price;
import com.inditex.domain.port.out.PriceRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Adaptador que implementa la interfaz PriceRepositoryPort, delegando las operaciones de acceso a datos
 * al repositorio PriceRepository.
 *
 * Esta clase actúa como un adaptador que traduce las llamadas de la interfaz de puerto de salida
 * (`PriceRepositoryPort`) a operaciones sobre el repositorio de datos específico (`PriceRepository`).
 */
@Primary
@Repository  // Indica que esta clase es un componente de repositorio para la persistencia de datos en Spring.
public class PriceRepositoryAdapter implements PriceRepositoryPort {

    // Instancia del repositorio de precios, inyectada a través del constructor.
    private final PriceRepository priceRepository;

    /**
     * Constructor que inyecta el repositorio de precios.
     *
     * @param priceRepository El repositorio de precios que será usado para acceder a los datos.
     */
    @Autowired  // Inyección de dependencias de Spring, lo que permite que el repositorio sea proporcionado automáticamente.
    public PriceRepositoryAdapter(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;  // Asigna el repositorio inyectado a la variable de instancia.
    }

    /**
     * Obtiene el precio más relevante para un producto y marca en una fecha determinada.
     *
     * Este método delega la llamada a la función `findTopPriceByBrandIdAndProductIdAndApplicationDateDefault`
     * del repositorio de datos para recuperar el precio más relevante.
     *
     * @param brandId       ID de la marca.
     * @param productId     ID del producto.
     * @param startDate     Fecha de aplicación para obtener el precio correspondiente.
     * @return Un Optional conteniendo el precio más relevante si existe, de lo contrario, está vacío.
     */
    @Override
    public Optional<Price> findTopPriceByBrandIdAndProductIdAndApplicationDate(Long brandId, Long productId, LocalDateTime startDate) {
        // Llama al método del repositorio que obtiene el precio más relevante, pasando los parámetros.
        return priceRepository.findTopPriceByBrandIdAndProductIdAndApplicationDateDefault(brandId, productId, startDate);
    }
}
