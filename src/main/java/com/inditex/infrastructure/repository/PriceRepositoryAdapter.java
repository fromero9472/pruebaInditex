package com.inditex.infrastructure.repository;

import com.inditex.application.exception.PriceNotFoundException;
import com.inditex.application.mapper.PriceMapper;
import com.inditex.domain.model.Price;
import com.inditex.infrastructure.entity.PriceEntity;
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
    private final PriceMapper priceMapper;

    @Autowired
    public PriceRepositoryAdapter(PriceRepository priceRepository, PriceMapper priceMapper) {
        this.priceRepository = priceRepository;
        this.priceMapper = priceMapper;
    }

    /**
     * Implementación del puerto para obtener el precio más relevante para un producto y marca en una fecha específica.
     */
    @Override
    public Price findTopPriceByBrandIdAndProductIdAndApplicationDate(Long brandId, Long productId, LocalDateTime applicationDate) {
        // Delegar la llamada al repositorio de Spring Data JPA
        Optional<PriceEntity> price = priceRepository.findTopByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                brandId, productId, applicationDate, applicationDate);
        PriceEntity entity =  price.orElseThrow(() -> new PriceNotFoundException("Precio no encontrado para el producto y marca especificados"));

        return priceMapper.convertToDTO(entity);
    }
}
