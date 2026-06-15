package com.example.ms_pagos.controller;

import com.example.ms_pagos.entity.Pago;
import com.example.ms_pagos.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los pagos")
    @ApiResponse(responseCode = "200", description = "Lista de pagos")
    public ResponseEntity<CollectionModel<EntityModel<Pago>>> listarTodos() {
        List<Pago> pagos = pagoService.obtenerTodos();

        List<EntityModel<Pago>> models = pagos.stream()
                .map(pago -> EntityModel.of(pago,
                        linkTo(methodOn(PagoController.class).buscarPorId(pago.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(models,
                linkTo(methodOn(PagoController.class).listarTodos()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pago por ID")
    @ApiResponse(responseCode = "200", description = "Pago encontrado")
    @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    public ResponseEntity<EntityModel<Pago>> buscarPorId(@PathVariable Integer id) {
        Pago pago = pagoService.obtenerPorId(id);
        EntityModel<Pago> model = EntityModel.of(pago);
        model.add(linkTo(methodOn(PagoController.class).buscarPorId(id)).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @PostMapping
    @Operation(summary = "Crear pago")
    @ApiResponse(responseCode = "201", description = "Pago creado")
    @ApiResponse(responseCode = "400", description = "Error de validación")
    public ResponseEntity<Pago> crearPago(@Valid @RequestBody Pago pago) {
        Pago nuevoPago = pagoService.registrarPago(pago);
        return new ResponseEntity<>(nuevoPago, HttpStatus.CREATED);
    }
}
