package com.example.ms_reservas.controller;

import com.example.ms_reservas.assemblers.ReservaModelAssembler;
import com.example.ms_reservas.dto.ReservaRequestDTO;
import com.example.ms_reservas.dto.ReservaResponseDTO;
import com.example.ms_reservas.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/reservas")
@RequiredArgsConstructor
@Tag(name = "Reservas", description = "Gestión de Reservas")
public class ReservaController {

    private final ReservaService service;
    private final ReservaModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todas las reservas")
    @ApiResponse(responseCode = "200", description = "Lista de reservas")
    public ResponseEntity<CollectionModel<EntityModel<ReservaResponseDTO>>> obtenerTodas() {
        List<EntityModel<ReservaResponseDTO>> models = service.listarTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(models,
                linkTo(methodOn(ReservaController.class).obtenerTodas()).withSelfRel()));
    }

    @GetMapping("/por-fecha")
    @Operation(summary = "Buscar reservas por fecha")
    @ApiResponse(responseCode = "200", description = "Lista de reservas por fecha")
    public ResponseEntity<CollectionModel<EntityModel<ReservaResponseDTO>>> obtenerPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<EntityModel<ReservaResponseDTO>> models = service.buscarDesdeFecha(fecha).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(models));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar reserva por ID")
    @ApiResponse(responseCode = "200", description = "Reserva encontrada")
    @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    public ResponseEntity<EntityModel<ReservaResponseDTO>> obtenerPorId(@PathVariable Integer id) {
        ReservaResponseDTO dto = service.obtenerPorId(id);
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @PostMapping
    @Operation(summary = "Crear reserva")
    @ApiResponse(responseCode = "201", description = "Reserva creada")
    @ApiResponse(responseCode = "400", description = "Error de validación")
    public ResponseEntity<EntityModel<ReservaResponseDTO>> crear(@Valid @RequestBody ReservaRequestDTO dto) {
        ReservaResponseDTO created = service.crear(dto);
        return new ResponseEntity<>(assembler.toModel(created), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reserva")
    @ApiResponse(responseCode = "200", description = "Reserva actualizada")
    @ApiResponse(responseCode = "400", description = "Error de validación")
    @ApiResponse(responseCode = "404", description = "Reserva no encontrado")
    public ResponseEntity<EntityModel<ReservaResponseDTO>> actualizar(@PathVariable Integer id, @Valid @RequestBody ReservaRequestDTO dto) {
        ReservaResponseDTO updated = service.actualizar(id, dto);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reserva")
    @ApiResponse(responseCode = "204", description = "Reserva eliminada")
    @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
