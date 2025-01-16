package com.inditex.application.controller;

import com.inditex.application.dto.PriceInDTO;
import com.inditex.application.dto.PriceOutDTO;
import com.inditex.domain.port.in.PriceServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para obtener los precios de productos.
 */
@RestController
@Tag(name = "Price", description = "Endpoints para la gestión de precios")
@RequestMapping("/v1/prices")
@Slf4j
public class PriceController {

    @Autowired
    private PriceServicePort priceService;

    /**
     * Endpoint para obtener el precio de un producto según los parámetros proporcionados.
     *
     * @param brandId ID de la marca del producto.
     * @param productId ID del producto.
     * @param applicationDate Fecha en la que se consulta el precio.
     * @return El precio del producto o un código de respuesta 204 si no se encuentra precio.
     */
    @Operation(
            summary = "Obtener precios",
            description = "Obtiene los precios basados en los parámetros proporcionados."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operación exitosa. Devuelve el precio.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PriceOutDTO.class))
            ),
            @ApiResponse(responseCode = "204", description = "Sin contenido. No se encontraron precios."),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta. Verifica los parámetros."),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido. El token no tiene permisos."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    @GetMapping("/getPrice")
    public ResponseEntity<PriceOutDTO> getPriceList(
            @Parameter(description = "ID de la marca", example = "1", required = true)
            @RequestParam Long brandId,

            @Parameter(description = "ID del producto", example = "35455", required = true)
            @RequestParam Long productId,

            @Parameter(description = "Fecha de aplicación", example = "2020-06-14-00.00.00", required = true)
            @RequestParam String applicationDate) {

        log.info("Solicitando precio para productoId: {}, brandId: {}, applicationDate: {}",
                productId, brandId, applicationDate);

        PriceInDTO params = new PriceInDTO(productId, brandId, applicationDate);
        PriceOutDTO priceOutDTO = priceService.getSinglePrice(params);

        return ResponseEntity.ok(priceOutDTO);

    }
}
