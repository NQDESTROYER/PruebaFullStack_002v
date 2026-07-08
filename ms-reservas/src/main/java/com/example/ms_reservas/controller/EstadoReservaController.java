package com.example.ms_reservas.controller;

import com.example.ms_reservas.dto.EstadoReservaRequestDTO;
import com.example.ms_reservas.dto.EstadoReservaResponseDTO;
import com.example.ms_reservas.service.EstadoReservaService;
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
@RequestMapping("/api/v1/estados-reserva")
@RequiredArgsConstructor
@Tag(name = "Estados de Reserva", description = "Gestión de Estados de Reserva")
public class EstadoReservaController {

    private final EstadoReservaService service;

    @GetMapping
    @Operation(summary = "Listar todos los estados de reserva")
    @ApiResponse(responseCode = "200", description = "Lista de estados")
    public ResponseEntity<CollectionModel<EntityModel<EstadoReservaResponseDTO>>> obtenerTodos() {
        List<EstadoReservaResponseDTO> dtos = service.listarTodos();

        List<EntityModel<EstadoReservaResponseDTO>> models = dtos.stream()
                .map(dto -> EntityModel.of(dto,
                        linkTo(methodOn(EstadoReservaController.class).obtenerPorId(dto.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(models,
                linkTo(methodOn(EstadoReservaController.class).obtenerTodos()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar estado de reserva por ID")
    @ApiResponse(responseCode = "200", description = "Estado encontrado")
    @ApiResponse(responseCode = "404", description = "Estado no encontrado")
    public ResponseEntity<EntityModel<EstadoReservaResponseDTO>> obtenerPorId(@PathVariable Integer id) {
        EstadoReservaResponseDTO dto = service.obtenerPorId(id);
        EntityModel<EstadoReservaResponseDTO> model = EntityModel.of(dto);
        model.add(linkTo(methodOn(EstadoReservaController.class).obtenerPorId(id)).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @PostMapping
    @Operation(summary = "Crear estado de reserva")
    @ApiResponse(responseCode = "201", description = "Estado creado")
    @ApiResponse(responseCode = "400", description = "Error de validación")
    public ResponseEntity<EstadoReservaResponseDTO> crear(@Valid @RequestBody EstadoReservaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar estado de reserva")
    @ApiResponse(responseCode = "200", description = "Estado actualizado")
    @ApiResponse(responseCode = "400", description = "Error de validación")
    @ApiResponse(responseCode = "404", description = "Estado no encontrado")
    public ResponseEntity<EstadoReservaResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody EstadoReservaRequestDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar estado de reserva")
    @ApiResponse(responseCode = "204", description = "Estado eliminado")
    @ApiResponse(responseCode = "404", description = "Estado no encontrado")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
