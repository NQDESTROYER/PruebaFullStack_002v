package com.example.ms_sucursales.controller;

import com.example.ms_sucursales.dto.SucursalRequestDTO;
import com.example.ms_sucursales.dto.SucursalResponseDTO;
import com.example.ms_sucursales.service.SucursalService;
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

@RestController
@RequestMapping("/api/v1/sucursales")
@RequiredArgsConstructor
@Tag(name = "Sucursales", description = "Gestión de Sucursales")
public class SucursalController {

    private final SucursalService sucursalService;

    @GetMapping
    @Operation(summary = "Listar todas las sucursales")
    @ApiResponse(responseCode = "200", description = "Lista de sucursales")
    public ResponseEntity<CollectionModel<EntityModel<SucursalResponseDTO>>> obtenerTodas() {
        List<SucursalResponseDTO> dtos = sucursalService.listarTodas();

        List<EntityModel<SucursalResponseDTO>> models = dtos.stream()
                .map(dto -> EntityModel.of(dto,
                        linkTo(methodOn(SucursalController.class).obtenerPorId(dto.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(models,
                linkTo(methodOn(SucursalController.class).obtenerTodas()).withSelfRel()));
    }

    @GetMapping("/operativas")
    @Operation(summary = "Listar sucursales operativas")
    @ApiResponse(responseCode = "200", description = "Lista de sucursales operativas")
    public ResponseEntity<CollectionModel<EntityModel<SucursalResponseDTO>>> obtenerOperativas() {
        List<SucursalResponseDTO> dtos = sucursalService.listarOperativas();

        List<EntityModel<SucursalResponseDTO>> models = dtos.stream()
                .map(dto -> EntityModel.of(dto,
                        linkTo(methodOn(SucursalController.class).obtenerPorId(dto.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(models));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sucursal por ID")
    @ApiResponse(responseCode = "200", description = "Sucursal encontrada")
    @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    public ResponseEntity<EntityModel<SucursalResponseDTO>> obtenerPorId(@PathVariable Integer id) {
        SucursalResponseDTO dto = sucursalService.obtenerPorId(id);
        EntityModel<SucursalResponseDTO> model = EntityModel.of(dto);
        model.add(linkTo(methodOn(SucursalController.class).obtenerPorId(id)).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @PostMapping
    @Operation(summary = "Registrar nueva sucursal")
    @ApiResponse(responseCode = "201", description = "Sucursal creada")
    @ApiResponse(responseCode = "400", description = "Error de validación")
    public ResponseEntity<SucursalResponseDTO> registrarNueva(@Valid @RequestBody SucursalRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sucursalService.crear(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar sucursal existente")
    @ApiResponse(responseCode = "200", description = "Sucursal actualizada")
    @ApiResponse(responseCode = "400", description = "Error de validación")
    @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    public ResponseEntity<SucursalResponseDTO> modificarExistente(
            @PathVariable Integer id,
            @Valid @RequestBody SucursalRequestDTO dto) {
        return ResponseEntity.ok(sucursalService.actualizar(id, dto));
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