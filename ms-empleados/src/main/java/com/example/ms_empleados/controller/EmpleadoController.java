package com.example.ms_empleados.controller;

import com.example.ms_empleados.dto.EmpleadoRequestDTO;
import com.example.ms_empleados.dto.EmpleadoResponseDTO;
import com.example.ms_empleados.service.IEmpleadoService;
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
@RequestMapping("/api/empleados")
@Tag(name = "Empleados", description = "Gestión de Empleados")
public class EmpleadoController {

    private final IEmpleadoService service;

    // Inyectamos el servicio mediante el constructor
    public EmpleadoController(IEmpleadoService service) {
        this.service = service;
    }

    // 1. CREAR EMPLEADO -> ej: POST http://localhost:8080/api/empleados
    @PostMapping
    @Operation(summary = "Crear empleado")
    @ApiResponse(responseCode = "201", description = "Empleado creado")
    @ApiResponse(responseCode = "400", description = "Error de validación")
    public ResponseEntity<EmpleadoResponseDTO> crear(@Valid @RequestBody EmpleadoRequestDTO request) {
        EmpleadoResponseDTO nuevoEmpleado = service.crear(request);
        return new ResponseEntity<>(nuevoEmpleado, HttpStatus.CREATED);
    }

    // 2. LISTAR TODOS -> ej: GET http://localhost:8080/api/empleados
    @GetMapping
    @Operation(summary = "Listar todos los empleados")
    @ApiResponse(responseCode = "200", description = "Lista de empleados")
    public ResponseEntity<CollectionModel<EntityModel<EmpleadoResponseDTO>>> listarTodos() {
        List<EmpleadoResponseDTO> dtos = service.listarTodos();

        List<EntityModel<EmpleadoResponseDTO>> models = dtos.stream()
                .map(dto -> EntityModel.of(dto,
                        linkTo(methodOn(EmpleadoController.class).buscarPorId(dto.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(models,
                linkTo(methodOn(EmpleadoController.class).listarTodos()).withSelfRel()));
    }

    // 3. BUSCAR POR ID -> ej: GET http://localhost:8080/api/empleados/{id}
    @GetMapping("/{id}")
    @Operation(summary = "Buscar empleado por ID")
    @ApiResponse(responseCode = "200", description = "Empleado encontrado")
    @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    public ResponseEntity<EntityModel<EmpleadoResponseDTO>> buscarPorId(@PathVariable Integer id) {
        EmpleadoResponseDTO dto = service.buscarPorId(id);
        EntityModel<EmpleadoResponseDTO> model = EntityModel.of(dto);
        model.add(linkTo(methodOn(EmpleadoController.class).buscarPorId(id)).withSelfRel());
        return ResponseEntity.ok(model);
    }

    // 4. ACTUALIZAR -> ej: PUT http://localhost:8080/api/empleados/{id}
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar empleado")
    @ApiResponse(responseCode = "200", description = "Empleado actualizado")
    @ApiResponse(responseCode = "400", description = "Error de validación")
    @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    public ResponseEntity<EmpleadoResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody EmpleadoRequestDTO request) {
        EmpleadoResponseDTO empleadoActualizado = service.actualizar(id, request);
        return ResponseEntity.ok(empleadoActualizado);
    }

    // 5. ELIMINAR -> ej: DELETE http://localhost:8080/api/empleados/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar empleado")
    @ApiResponse(responseCode = "204", description = "Empleado eliminado")
    @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
