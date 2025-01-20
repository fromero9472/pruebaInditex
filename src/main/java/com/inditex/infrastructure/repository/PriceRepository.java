package com.inditex.infrastructure.repository;

import com.inditex.infrastructure.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Repositorio para la entidad Price, que extiende JpaRepository
 * para operaciones CRUD y consultas personalizadas.
 */
public interface PriceRepository extends JpaRepository<PriceEntity, Long> {

    /**
     * Encuentra el precio más relevante para un producto y marca
     * según la fecha de aplicación, asegurándose de que el precio
     * esté dentro del rango de fechas (startDate <= applicationDate <= endDate)
     * y con la mayor prioridad (ordenado de mayor a menor prioridad).
     *
     * @param brandId         ID de la marca.
     * @param productId       ID del producto.
     * @param applicationDate Fecha de la aplicación (se compara con startDate y endDate).
     * @return Precio más relevante (opcional).
     */
    Optional<PriceEntity> findTopByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
            Long brandId,
            Long productId,
            LocalDateTime applicationDate,
            LocalDateTime applicationEndDate);

}
