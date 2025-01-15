package com.inditex.application.controller;

import com.inditex.application.dto.PriceInDTO;
import com.inditex.application.dto.PriceOutDTO;
import com.inditex.domain.port.in.PriceServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Controlador que gestiona los endpoints relacionados con la obtención de precios de productos.
 * Permite consultar el precio de un producto específico basado en parámetros como el ID de la marca,
 * el ID del producto y la fecha de aplicación del precio.
 */
@RestController
@Tag(name = "Price", description = "Endpoints para la gestión de precios")
@RequestMapping("/v1/prices")
@Slf4j  // Anotación de Lombok para habilitar logging en la clase.
public class PriceController {

    // Servicio para obtener el precio del producto
    @Autowired
    private PriceServicePort priceService;

    /**
     * Endpoint que devuelve los precios basados en los parámetros proporcionados.
     *
     * @param brandId El ID de la marca del producto.
     * @param productId El ID del producto.
     * @param applicationDate La fecha en la que se desea conocer el precio.
     * @return Un objeto PriceOutDTO que contiene la información del precio o un código de respuesta 204 si no se encuentra precio.
     *
     * <p>Este método recibe los parámetros necesarios para consultar el precio de un producto. Si encuentra un precio
     * aplicable para los parámetros proporcionados, devuelve un objeto con los detalles del precio. Si no se encuentra
     * ningún precio, devuelve un código HTTP 204 (Sin Contenido).</p>
     */
    @Operation(
            summary = "Obtener precios",
            description = "Obtiene los precios basados en los parámetros proporcionados, como `productId`, `brandId` y `applicationDate`."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operación exitosa. Devuelve los precios.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PriceOutDTO.class))
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Sin contenido. No se encontraron precios para los parámetros proporcionados."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Solicitud incorrecta. Verifica los parámetros."
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso prohibido. El token no tiene permisos suficientes."
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor. Consulta los logs para más detalles."
            )
    })
    @GetMapping(value = "/getPrice", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PriceOutDTO> getPriceList(
            @Parameter(description = "ID de la marca", example = "1")
            @RequestParam(required = true) Long brandId,

            @Parameter(description = "ID del producto", example = "35455")
            @RequestParam(required = true) Long productId,

            @Parameter(description = "Fecha de aplicación", example = "2020-06-14-00.00.00")
            @RequestParam(required = true) String applicationDate) {

        log.info("Recibiendo solicitud para obtener precio con parámetros -> productId: {}, brandId: {}, applicationDate: {}",
                productId, brandId, applicationDate);  // Log para rastrear la solicitud con parámetros.

        // Crear un objeto DTO con los parámetros proporcionados
        PriceInDTO params = new PriceInDTO(productId, brandId, applicationDate);

        // Llamar al servicio para obtener el precio para los parámetros dados
        PriceOutDTO priceOutDTO = priceService.getSinglePrice(params);

        // Si se encontró un precio, devolverlo con respuesta HTTP 200
        log.info("Precio encontrado para el productoId: {} y brandId: {}", productId, brandId);
        return ResponseEntity.ok(priceOutDTO);
    }
}
