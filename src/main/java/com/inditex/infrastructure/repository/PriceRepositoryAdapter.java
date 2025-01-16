package com.inditex.infrastructure.repository;

import com.inditex.domain.exception.PriceNotFoundException;
import com.inditex.domain.model.Price;
import com.inditex.domain.port.out.PriceRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Adaptador que implementa PriceRepositoryPort, delegando operaciones al repositorio PriceRepository.
 */
@Primary
@Repository
public class PriceRepositoryAdapter implements PriceRepositoryPort {

    private final PriceRepository priceRepository;

    @Autowired
    public PriceRepositoryAdapter(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    /**
     * Implementación del puerto para obtener el precio más relevante para un producto y marca en una fecha específica.
     */
    @Override
    public Price findTopPriceByBrandIdAndProductIdAndApplicationDate(Long brandId, Long productId, LocalDateTime applicationDate) {
        // Delegar la llamada al repositorio de Spring Data JPA
        Optional<Price> price = priceRepository.findTopByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                brandId, productId, applicationDate, applicationDate);

        // Si no se encuentra un precio, lanzar una excepción o devolver un valor adecuado
        return price.orElseThrow(() -> new PriceNotFoundException("Precio no encontrado para el producto y marca especificados"));
    }
}
