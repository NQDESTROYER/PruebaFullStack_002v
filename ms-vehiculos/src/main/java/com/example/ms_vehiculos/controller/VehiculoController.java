package com.example.ms_vehiculos.controller;

import com.example.ms_vehiculos.assemblers.VehiculoModelAssembler;
import com.example.ms_vehiculos.dto.VehiculoRequestDTO;
import com.example.ms_vehiculos.dto.VehiculosResponseDTO;
import com.example.ms_vehiculos.service.IVehiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RequestMapping("/api/vehiculos")
@RestController
@Tag(name = "Vehículos", description = "Gestión de Vehículos")
public class VehiculoController {

    private final IVehiculoService vehiculoService;
    private final VehiculoModelAssembler assembler;

    @PostMapping
    @Operation(summary = "Crear vehículo")
    @ApiResponse(responseCode = "201", description = "Vehículo creado")
    @ApiResponse(responseCode = "400", description = "Error de validación")
    public ResponseEntity<EntityModel<VehiculosResponseDTO>> crear(@Valid @RequestBody VehiculoRequestDTO dto){
        VehiculosResponseDTO nuevoVehiculo = vehiculoService.crear(dto);
        return new ResponseEntity<>(assembler.toModel(nuevoVehiculo), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar todos los vehículos")
    @ApiResponse(responseCode = "200", description = "Lista de vehículos")
    public ResponseEntity<CollectionModel<EntityModel<VehiculosResponseDTO>>> listarTodos() {
        List<EntityModel<VehiculosResponseDTO>> models = vehiculoService.listarTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(models, 
                linkTo(methodOn(VehiculoController.class).listarTodos()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar vehículo por ID")
    @ApiResponse(responseCode = "200", description = "Vehículo encontrado")
    @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    public ResponseEntity<EntityModel<VehiculosResponseDTO>> buscarPorId(@PathVariable Integer id) {
        VehiculosResponseDTO vehiculo = vehiculoService.buscarPorId(id);
        return ResponseEntity.ok(assembler.toModel(vehiculo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar vehículo")
    @ApiResponse(responseCode = "200", description = "Vehículo actualizado")
    @ApiResponse(responseCode = "400", description = "Error de validación")
    @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    public ResponseEntity<EntityModel<VehiculosResponseDTO>> actualizar(@PathVariable Integer id, @Valid @RequestBody VehiculoRequestDTO dto) {
        VehiculosResponseDTO vehiculoActualizado = vehiculoService.actualizar(id, dto);
        return ResponseEntity.ok(assembler.toModel(vehiculoActualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar vehículo")
    @ApiResponse(responseCode = "204", description = "Vehículo eliminado")
    @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        vehiculoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
