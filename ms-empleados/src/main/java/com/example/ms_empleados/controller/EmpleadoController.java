package com.example.ms_empleados.controller;

import com.example.ms_empleados.assemblers.EmpleadoModelAssembler;
import com.example.ms_empleados.dto.EmpleadoRequestDTO;
import com.example.ms_empleados.dto.EmpleadoResponseDTO;
import com.example.ms_empleados.service.IEmpleadoService;
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
@RequestMapping("/api/v1/empleados")
@Tag(name = "Empleados", description = "Gestión de Empleados")
@RequiredArgsConstructor
@Slf4j // Agregamos la anotación de Lombok para SLF4J
public class EmpleadoController {

    private final IEmpleadoService service;
    private final EmpleadoModelAssembler assembler;

    @PostMapping
    @Operation(summary = "Crear empleado")
    @ApiResponse(responseCode = "201", description = "Empleado creado")
    @ApiResponse(responseCode = "400", description = "Error de validación")
    public ResponseEntity<EntityModel<EmpleadoResponseDTO>> crear(@Valid @RequestBody EmpleadoRequestDTO request) {
        log.info("REST request para crear un nuevo empleado con RUT: {}", request.getRut());
        EmpleadoResponseDTO nuevoEmpleado = service.crear(request);
        return new ResponseEntity<>(assembler.toModel(nuevoEmpleado), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar todos los empleados")
    @ApiResponse(responseCode = "200", description = "Lista de empleados")
    public ResponseEntity<CollectionModel<EntityModel<EmpleadoResponseDTO>>> listarTodos() {
        log.info("REST request para listar todos los empleados");
        List<EntityModel<EmpleadoResponseDTO>> models = service.listarTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(models,
                linkTo(methodOn(EmpleadoController.class).listarTodos()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar empleado por ID")
    @ApiResponse(responseCode = "200", description = "Empleado encontrado")
    @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    public ResponseEntity<EntityModel<EmpleadoResponseDTO>> buscarPorId(@PathVariable Integer id) {
        log.info("REST request para buscar empleado con ID: {}", id);
        EmpleadoResponseDTO dto = service.buscarPorId(id);
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar empleado")
    @ApiResponse(responseCode = "200", description = "Empleado actualizado")
    @ApiResponse(responseCode = "400", description = "Error de validación")
    @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    public ResponseEntity<EntityModel<EmpleadoResponseDTO>> actualizar(@PathVariable Integer id, @Valid @RequestBody EmpleadoRequestDTO request) {
        log.info("REST request para actualizar empleado con ID: {}", id);
        EmpleadoResponseDTO empleadoActualizado = service.actualizar(id, request);
        return ResponseEntity.ok(assembler.toModel(empleadoActualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar empleado")
    @ApiResponse(responseCode = "204", description = "Empleado eliminado")
    @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("REST request para eliminar empleado con ID: {}", id);
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/activos/{anio}")
    @Operation(summary = "Buscar empleados activos por año de contratación (Native Query)")
    @ApiResponse(responseCode = "200", description = "Lista de empleados encontrada")
    public ResponseEntity<CollectionModel<EntityModel<EmpleadoResponseDTO>>> buscarActivosPorAnio(@PathVariable Integer anio) {
        log.info("REST request para buscar empleados activos del año: {}", anio);
        List<EntityModel<EmpleadoResponseDTO>> models = service.buscarActivosPorAnio(anio).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(models,
                linkTo(methodOn(EmpleadoController.class).buscarActivosPorAnio(anio)).withSelfRel()));
    }
}