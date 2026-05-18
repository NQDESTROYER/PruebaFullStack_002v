package com.example.ms_reservas.controller;

import com.example.ms_reservas.dto.EstadoReservaRequestDTO;
import com.example.ms_reservas.dto.EstadoReservaResponseDTO;
import com.example.ms_reservas.service.EstadoReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estados-reserva")
@RequiredArgsConstructor
public class EstadoReservaController {

    private final EstadoReservaService service;

    @GetMapping
    public ResponseEntity<List<EstadoReservaResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoReservaResponseDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<EstadoReservaResponseDTO> crear(@Valid @RequestBody EstadoReservaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoReservaResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody EstadoReservaRequestDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
