package com.inditex.infrastructure.repository;

import com.inditex.domain.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio de datos para la entidad Price.
 * Extiende JpaRepository para obtener funcionalidades CRUD básicas
 * y define métodos específicos de consulta.
 */
public interface PriceRepository extends JpaRepository<Price, Long> {

    /**
     * Devuelve un precio aplicable al producto y marca en la fecha de aplicación dada,
     * asegurando que se devuelva el de mayor prioridad.
     *
     * @param brandId       ID de la marca.
     * @param productId     ID del producto.
     * @param applicationDate Fecha de aplicación para la cual se obtiene el precio.
     * @return Lista de precios relevantes (ordenados por prioridad).
     */
    @Query("SELECT p FROM Price p WHERE p.brandId = :brandId " +  // Filtra por la marca.
            "AND p.productId = :productId " +  // Filtra por el producto.
            "AND :applicationDate BETWEEN p.startDate AND p.endDate " +  // Verifica que la fecha de aplicación esté dentro del rango de fechas.
            "ORDER BY p.priority DESC, p.startDate DESC, p.id DESC")  // Ordena primero por prioridad descendente, luego por fecha de inicio y finalmente por ID.
    List<Price> findTopPricesByBrandIdAndProductIdAndApplicationDate(  // Método que recibe los parámetros para obtener los precios.
                                                                       @Param("brandId") Long brandId,  // Parámetro de la marca.
                                                                       @Param("productId") Long productId,  // Parámetro del producto.
                                                                       @Param("applicationDate") LocalDateTime applicationDate);  // Parámetro de la fecha de aplicación.

    /**
     * Devuelve el precio más relevante (con mayor prioridad) para ese producto y marca
     * en la fecha de aplicación.
     *
     * @param brandId       ID de la marca.
     * @param productId     ID del producto.
     * @param applicationDate Fecha de aplicación.
     * @return Un Optional conteniendo el precio más relevante si existe.
     */
    default Optional<Price> findTopPriceByBrandIdAndProductIdAndApplicationDateDefault(  // Método por defecto para obtener un solo precio.
                                                                                         Long brandId, Long productId, LocalDateTime applicationDate) {

        // Llama al método findTopPricesByBrandIdAndProductIdAndApplicationDate y obtiene la lista de precios.
        List<Price> prices = findTopPricesByBrandIdAndProductIdAndApplicationDate(brandId, productId, applicationDate);

        // Si la lista de precios no está vacía, devuelve el primer precio como un Optional.
        return prices.isEmpty() ? Optional.empty() : Optional.of(prices.get(0));  // Devuelve el precio más relevante envuelto en un Optional.
    }
}
