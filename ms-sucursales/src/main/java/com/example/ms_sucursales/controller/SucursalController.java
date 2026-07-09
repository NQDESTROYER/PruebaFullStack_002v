package com.example.ms_sucursales.controller;

import com.example.ms_sucursales.assemblers.SucursalModelAssembler;
import com.example.ms_sucursales.dto.SucursalRequestDTO;
import com.example.ms_sucursales.dto.SucursalResponseDTO;
import com.example.ms_sucursales.service.SucursalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/sucursales")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Sucursales", description = "Gestión de Sucursales")
public class SucursalController {

    private final SucursalService sucursalService;
    private final SucursalModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todas las sucursales")
    @ApiResponse(responseCode = "200", description = "Lista de sucursales")
    public ResponseEntity<CollectionModel<EntityModel<SucursalResponseDTO>>> obtenerTodas() {
        List<EntityModel<SucursalResponseDTO>> models = sucursalService.listarTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(models,
                linkTo(methodOn(SucursalController.class).obtenerTodas()).withSelfRel()));
    }

    @GetMapping("/operativas")
    @Operation(summary = "Listar sucursales operativas")
    @ApiResponse(responseCode = "200", description = "Lista de sucursales operativas")
    public ResponseEntity<CollectionModel<EntityModel<SucursalResponseDTO>>> obtenerOperativas() {
        List<EntityModel<SucursalResponseDTO>> models = sucursalService.listarOperativas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(models));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sucursal por ID")
    @ApiResponse(responseCode = "200", description = "Sucursal encontrada")
    @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    public ResponseEntity<EntityModel<SucursalResponseDTO>> obtenerPorId(@PathVariable Integer id) {
        SucursalResponseDTO dto = sucursalService.obtenerPorId(id);
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @PostMapping
    @Operation(summary = "Registrar nueva sucursal")
    @ApiResponse(responseCode = "201", description = "Sucursal creada")
    @ApiResponse(responseCode = "400", description = "Error de validación")
    public ResponseEntity<EntityModel<SucursalResponseDTO>> registrarNueva(@Valid @RequestBody SucursalRequestDTO dto) {
        SucursalResponseDTO created = sucursalService.crear(dto);
        return new ResponseEntity<>(assembler.toModel(created), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar sucursal existente")
    @ApiResponse(responseCode = "200", description = "Sucursal actualizada")
    @ApiResponse(responseCode = "400", description = "Error de validación")
    @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    public ResponseEntity<EntityModel<SucursalResponseDTO>> modificarExistente(
            @PathVariable Integer id,
            @Valid @RequestBody SucursalRequestDTO dto) {
        SucursalResponseDTO updated = sucursalService.actualizar(id, dto);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borrar sucursal")
    @ApiResponse(responseCode = "204", description = "Sucursal eliminada")
    @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    public ResponseEntity<Void> borrar(@PathVariable Integer id) {
        sucursalService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
