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
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Tag(name = "Reservas", description = "Gestión de Reservas")
public class ReservaController {

    private final ReservaService service;
    private final ReservaModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todas las reservas")
    @ApiResponse(responseCode = "200", description = "Lista de reservas")
    public ResponseEntity<CollectionModel<EntityModel<ReservaResponseDTO>>> obtenerTodas() {
        log.info("Petición REST: Recibida solicitud para listar todas las reservas");

        List<EntityModel<ReservaResponseDTO>> models = service.listarTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        log.info("Petición REST: Se encontraron y procesaron {} reservas", models.size());
        return ResponseEntity.ok(CollectionModel.of(models,
                linkTo(methodOn(ReservaController.class).obtenerTodas()).withSelfRel()));
    }

    @GetMapping("/por-fecha")
    @Operation(summary = "Buscar reservas por fecha")
    @ApiResponse(responseCode = "200", description = "Lista de reservas a partir de una fecha")
    public ResponseEntity<CollectionModel<EntityModel<ReservaResponseDTO>>> obtenerPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        log.info("Petición REST: Recibida solicitud para buscar reservas desde la fecha: {}", fecha);

        List<EntityModel<ReservaResponseDTO>> models = service.buscarDesdeFecha(fecha).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        log.info("Petición REST: Se encontraron {} reservas para el criterio de fecha especificado", models.size());
        return ResponseEntity.ok(CollectionModel.of(models,
                linkTo(methodOn(ReservaController.class).obtenerPorFecha(fecha)).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar reserva por ID")
    @ApiResponse(responseCode = "200", description = "Reserva encontrada")
    @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    public ResponseEntity<EntityModel<ReservaResponseDTO>> obtenerPorId(@PathVariable Integer id) {
        log.info("Petición REST: Buscando reserva con ID: {}", id);
        ReservaResponseDTO dto = service.obtenerPorId(id);

        log.info("Petición REST: Reserva encontrada exitosamente con ID: {}", id);
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @PostMapping
    @Operation(summary = "Crear reserva")
    @ApiResponse(responseCode = "201", description = "Reserva creada")
    @ApiResponse(responseCode = "400", description = "Error de validación o regla de negocio")
    public ResponseEntity<EntityModel<ReservaResponseDTO>> crear(@Valid @RequestBody ReservaRequestDTO dto) {
        log.info("Petición REST: Iniciando la creación de una nueva reserva con código: {}", dto.getCodigoReserva());
        ReservaResponseDTO created = service.crear(dto);

        log.info("Petición REST: Reserva creada exitosamente asignando ID: {}", created.getId());
        return new ResponseEntity<>(assembler.toModel(created), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reserva")
    @ApiResponse(responseCode = "200", description = "Reserva actualizada")
    @ApiResponse(responseCode = "400", description = "Error de validación")
    @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    public ResponseEntity<EntityModel<ReservaResponseDTO>> actualizar(@PathVariable Integer id, @Valid @RequestBody ReservaRequestDTO dto) {
        log.info("Petición REST: Solicitud para actualizar la reserva con ID: {}", id);
        ReservaResponseDTO updated = service.actualizar(id, dto);

        log.info("Petición REST: Reserva con ID {} actualizada de forma correcta", id);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reserva")
    @ApiResponse(responseCode = "204", description = "Reserva eliminada")
    @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("Petición REST: Solicitud recibida para eliminar la reserva con ID: {}", id);
        service.eliminar(id);

        log.info("Petición REST: Reserva con ID {} eliminada con éxito", id);
        return ResponseEntity.noContent().build();
    }
}