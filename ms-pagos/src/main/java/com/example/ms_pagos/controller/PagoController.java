package com.example.ms_pagos.controller;

import com.example.ms_pagos.assemblers.PagoModelAssembler;
import com.example.ms_pagos.dto.PagoRequestDTO;
import com.example.ms_pagos.dto.PagoResponseDTO;
import com.example.ms_pagos.service.PagoService;
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
@RequestMapping("/api/pagos")
@Tag(name = "Pagos", description = "Gestión de Pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;
    private final PagoModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todos los pagos")
    @ApiResponse(responseCode = "200", description = "Lista de pagos")
    public ResponseEntity<CollectionModel<EntityModel<PagoResponseDTO>>> listarTodos() {
        List<EntityModel<PagoResponseDTO>> models = pagoService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(models,
                linkTo(methodOn(PagoController.class).listarTodos()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pago por ID")
    @ApiResponse(responseCode = "200", description = "Pago encontrado")
    @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    public ResponseEntity<EntityModel<PagoResponseDTO>> buscarPorId(@PathVariable Integer id) {
        PagoResponseDTO dto = pagoService.obtenerPorId(id);
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @PostMapping
    @Operation(summary = "Crear pago")
    @ApiResponse(responseCode = "201", description = "Pago creado")
    @ApiResponse(responseCode = "400", description = "Error de validación")
    public ResponseEntity<EntityModel<PagoResponseDTO>> crearPago(@Valid @RequestBody PagoRequestDTO dto) {
        PagoResponseDTO nuevoPago = pagoService.registrarPago(dto);
        return new ResponseEntity<>(assembler.toModel(nuevoPago), HttpStatus.CREATED);
    }
}
