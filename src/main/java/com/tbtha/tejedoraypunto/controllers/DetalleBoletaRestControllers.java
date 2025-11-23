package com.tbtha.tejedoraypunto.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tbtha.tejedoraypunto.entities.DetalleBoleta;
import com.tbtha.tejedoraypunto.services.DetalleBoletaServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/detalles-boleta")
@Tag(name = "Detalles de Boleta", description = "API para gestión de detalles de boletas")
public class DetalleBoletaRestControllers {

    @Autowired
    private DetalleBoletaServices detalleBoletaServices;

    @PostMapping
    @Operation(summary = "Crear un nuevo detalle de boleta", description = "Crea un nuevo detalle para una boleta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalle creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<DetalleBoleta> crearDetalle(
        @Parameter(description = "Datos del detalle a crear", required = true)
        @RequestBody DetalleBoleta detalleBoleta) {
        DetalleBoleta nuevoDetalle = detalleBoletaServices.crear(detalleBoleta);
        return ResponseEntity.ok(nuevoDetalle);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle por ID", description = "Obtiene un detalle específico por su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalle encontrado"),
        @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    public ResponseEntity<DetalleBoleta> obtenerDetallePorId(
        @Parameter(description = "ID del detalle", required = true, example = "1")
        @PathVariable Long id) {
        DetalleBoleta detalle = detalleBoletaServices.obtenerId(id);
        return ResponseEntity.ok(detalle);
    }

    @GetMapping
    @Operation(summary = "Listar todos los detalles", description = "Obtiene una lista de todos los detalles de boletas")
    @ApiResponse(responseCode = "200", description = "Lista de detalles obtenida exitosamente")
    public ResponseEntity<List<DetalleBoleta>> listarDetalles() {
        List<DetalleBoleta> detalles = detalleBoletaServices.listarTodas();
        return ResponseEntity.ok(detalles);
    }

    @GetMapping("/boleta/{boletaId}")
    @Operation(summary = "Listar detalles por boleta", description = "Obtiene los detalles de una boleta específica")
    @ApiResponse(responseCode = "200", description = "Detalles de la boleta obtenidos exitosamente")
    public ResponseEntity<List<DetalleBoleta>> listarDetallesPorBoleta(
        @Parameter(description = "ID de la boleta", required = true, example = "1")
        @PathVariable Long boletaId) {
        List<DetalleBoleta> detalles = detalleBoletaServices.listarPorBoleta(boletaId);
        return ResponseEntity.ok(detalles);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar detalle", description = "Actualiza un detalle de boleta existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalle actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    public ResponseEntity<DetalleBoleta> actualizarDetalle(
        @Parameter(description = "ID del detalle a actualizar", required = true, example = "1")
        @PathVariable Long id,
        @Parameter(description = "Nuevos datos del detalle", required = true)
        @RequestBody DetalleBoleta detalleActualizado) {
        DetalleBoleta detalle = detalleBoletaServices.actualizar(id, detalleActualizado);
        return ResponseEntity.ok(detalle);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar detalle", description = "Elimina un detalle de boleta del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Detalle eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    public ResponseEntity<Void> eliminarDetalle(
        @Parameter(description = "ID del detalle a eliminar", required = true, example = "1")
        @PathVariable Long id) {
        detalleBoletaServices.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
