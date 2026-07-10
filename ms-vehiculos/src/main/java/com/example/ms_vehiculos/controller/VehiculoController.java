package com.example.ms_vehiculos.controller;

import com.example.ms_vehiculos.dto.VehiculoRequestDTO;
import com.example.ms_vehiculos.dto.VehiculosResponseDTO;
import com.example.ms_vehiculos.model.Categoria;
import com.example.ms_vehiculos.service.IVehiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@RequiredArgsConstructor
@Tag(name = "Vehículos", description = "API de gestión de vehículos y sus categorías")
public class VehiculoController {

    private final IVehiculoService vehiculoService;

    // 1. LISTAR TODOS LOS VEHÍCULOS
    @GetMapping
    @Operation(summary = "Listar todos los vehículos")
    @ApiResponse(responseCode = "200", description = "Lista obtenida con éxito")
    public ResponseEntity<List<VehiculosResponseDTO>> listarTodos() {
        return ResponseEntity.ok(vehiculoService.listarTodos());
    }

    // 2. BUSCAR VEHÍCULO POR ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar vehículo por ID")
    @ApiResponse(responseCode = "200", description = "Vehículo encontrado")
    @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    public ResponseEntity<VehiculosResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(vehiculoService.buscarPorId(id));
    }

    // 3. CREAR UN NUEVO VEHÍCULO
    @PostMapping
    @Operation(summary = "Crear un vehículo")
    @ApiResponse(responseCode = "201", description = "Vehículo creado con éxito")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    public ResponseEntity<VehiculosResponseDTO> crear(@Valid @RequestBody VehiculoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculoService.crear(dto));
    }

    // 4. ACTUALIZAR UN VEHÍCULO EXISTENTE
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un vehículo")
    @ApiResponse(responseCode = "200", description = "Vehículo actualizado con éxito")
    @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    public ResponseEntity<VehiculosResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody VehiculoRequestDTO dto) {
        return ResponseEntity.ok(vehiculoService.actualizar(id, dto));
    }

    // 5. ELIMINAR UN VEHÍCULO
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un vehículo")
    @ApiResponse(responseCode = "204", description = "Vehículo eliminado con éxito")
    @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        vehiculoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ===================================================================
    // SUB-RECURSOS: CATEGORÍA DEL VEHÍCULO
    // ===================================================================

    // 6. OBTENER CATEGORÍA DE UN VEHÍCULO (GET)
    @GetMapping("/{id}/categoria")
    @Operation(summary = "Obtener categoría del vehículo")
    @ApiResponse(responseCode = "200", description = "Categoría obtenida con éxito")
    @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    public ResponseEntity<Categoria> obtenerCategoria(@PathVariable Integer id) {
        return ResponseEntity.ok(vehiculoService.obtenerCategoriaPorVehiculoId(id));
    }

    // 7. ACTUALIZAR CATEGORÍA DE UN VEHÍCULO (PUT)
    @PutMapping("/{id}/categoria")
    @Operation(summary = "Actualizar categoría del vehículo", description = "Cambia la categoría enviando un JSON con el campo 'categoriaId'")
    @ApiResponse(responseCode = "200", description = "Categoría actualizada con éxito")
    @ApiResponse(responseCode = "404", description = "Vehículo o Categoría no encontrada")
    public ResponseEntity<Categoria> actualizarCategoria(
            @PathVariable Integer id,
            @RequestBody java.util.Map<String, Integer> body) {

        Integer nuevaCategoriaId = body.get("categoriaId");
        if (nuevaCategoriaId == null) {
            throw new IllegalArgumentException("El campo 'categoriaId' es obligatorio en el cuerpo de la solicitud");
        }

        return ResponseEntity.ok(vehiculoService.actualizarCategoria(id, nuevaCategoriaId));
    }
}