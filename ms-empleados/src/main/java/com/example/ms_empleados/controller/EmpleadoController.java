package com.example.ms_empleados.controller;

import com.example.ms_empleados.dto.EmpleadoRequestDTO;
import com.example.ms_empleados.dto.EmpleadoResponseDTO;
import com.example.ms_empleados.service.IEmpleadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final IEmpleadoService service;

    // Inyectamos el servicio mediante el constructor
    public EmpleadoController(IEmpleadoService service) {
        this.service = service;
    }

    // 1. CREAR EMPLEADO -> POST http://localhost:8080/api/empleados
    @PostMapping
    public ResponseEntity<EmpleadoResponseDTO> crear(@Valid @RequestBody EmpleadoRequestDTO request) {
        EmpleadoResponseDTO nuevoEmpleado = service.crear(request);
        return new ResponseEntity<>(nuevoEmpleado, HttpStatus.CREATED); // Devuelve Estado 201 Created
    }

    // 2. LISTAR TODOS -> GET http://localhost:8080/api/empleados
    @GetMapping
    public ResponseEntity<List<EmpleadoResponseDTO>> listarTodos() {
        List<EmpleadoResponseDTO> empleados = service.listarTodos();
        return ResponseEntity.ok(empleados); // Devuelve Estado 200 OK
    }

    // 3. BUSCAR POR ID -> GET http://localhost:8080/api/empleados/{id}
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> buscarPorId(@PathVariable Integer id) {
        EmpleadoResponseDTO empleado = service.buscarPorId(id);
        return ResponseEntity.ok(empleado); // Devuelve Estado 200 OK
    }

    // 4. ACTUALIZAR -> PUT http://localhost:8080/api/empleados/{id}
    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody EmpleadoRequestDTO request) {
        EmpleadoResponseDTO empleadoActualizado = service.actualizar(id, request);
        return ResponseEntity.ok(empleadoActualizado); // Devuelve Estado 200 OK
    }

    // 5. ELIMINAR -> DELETE http://localhost:8080/api/empleados/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build(); // Devuelve Estado 204 No Content (borrado con éxito)
    }
}
