package com.tbtha.tejedoraypunto.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tbtha.tejedoraypunto.entities.Boleta;
import com.tbtha.tejedoraypunto.services.BoletaServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/boletas")
@Tag(name = "Boletas", description = "API para gestión de boletas y compras")
public class BoletaRestControllers {

    @Autowired
    private BoletaServices boletaServices;

    @PostMapping
    @Operation(summary = "Crear una nueva boleta", description = "Crea una nueva boleta con sus detalles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Boleta creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de boleta inválidos")
    })
    public ResponseEntity<Boleta> crearBoleta(
        @Parameter(description = "Datos de la boleta a crear", required = true)
        @RequestBody Boleta boleta) {
        Boleta nuevaBoleta = boletaServices.crear(boleta);
        return ResponseEntity.ok(nuevaBoleta);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener boleta por ID", description = "Obtiene una boleta específica por su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Boleta encontrada"),
        @ApiResponse(responseCode = "404", description = "Boleta no encontrada")
    })
    public ResponseEntity<Boleta> obtenerBoletaPorId(
        @Parameter(description = "ID de la boleta", required = true, example = "1")
        @PathVariable Long id) {
        Boleta boleta = boletaServices.obtenerId(id);
        return ResponseEntity.ok(boleta);
    }

    @GetMapping
    @Operation(summary = "Listar todas las boletas", description = "Obtiene una lista de todas las boletas")
    @ApiResponse(responseCode = "200", description = "Lista de boletas obtenida exitosamente")
    public ResponseEntity<List<Boleta>> listarBoletas() {
        List<Boleta> boletas = boletaServices.listarTodas();
        return ResponseEntity.ok(boletas);
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar boletas por usuario", description = "Obtiene las boletas de un usuario específico")
    @ApiResponse(responseCode = "200", description = "Boletas del usuario obtenidas exitosamente")
    public ResponseEntity<List<Boleta>> listarBoletasPorUsuario(
        @Parameter(description = "ID del usuario", required = true, example = "1")
        @PathVariable Long usuarioId) {
        List<Boleta> boletas = boletaServices.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(boletas);
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Listar boletas por estado", description = "Obtiene las boletas según su estado")
    @ApiResponse(responseCode = "200", description = "Boletas filtradas por estado")
    public ResponseEntity<List<Boleta>> listarBoletasPorEstado(
        @Parameter(description = "Estado de las boletas", required = true, example = "PENDIENTE")
        @PathVariable String estado) {
        List<Boleta> boletas = boletaServices.listarPorEstado(estado);
        return ResponseEntity.ok(boletas);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar boleta", description = "Actualiza los datos de una boleta existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Boleta actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Boleta no encontrada")
    })
    public ResponseEntity<Boleta> actualizarBoleta(
        @Parameter(description = "ID de la boleta a actualizar", required = true, example = "1")
        @PathVariable Long id,
        @Parameter(description = "Nuevos datos de la boleta", required = true)
        @RequestBody Boleta boletaActualizada) {
        Boleta boleta = boletaServices.actualizar(id, boletaActualizada);
        return ResponseEntity.ok(boleta);
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de boleta", description = "Actualiza el estado de una boleta")
    @ApiResponse(responseCode = "200", description = "Estado de boleta actualizado")
    public ResponseEntity<Boleta> cambiarEstado(
        @Parameter(description = "ID de la boleta", required = true, example = "1")
        @PathVariable Long id,
        @Parameter(description = "Nuevo estado", required = true)
        @RequestParam String estado) {
        Boleta boleta = boletaServices.cambiarEstado(id, estado);
        return ResponseEntity.ok(boleta);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar boleta", description = "Elimina una boleta del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Boleta eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Boleta no encontrada")
    })
    public ResponseEntity<Void> eliminarBoleta(
        @Parameter(description = "ID de la boleta a eliminar", required = true, example = "1")
        @PathVariable Long id) {
        boletaServices.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
