package com.example.ms_reservas.controller;

import com.example.ms_reservas.dto.ReservaRequestDTO;
import com.example.ms_reservas.dto.ReservaResponseDTO;
import com.example.ms_reservas.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService service;

    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    // Endpoint adicional para probar la Query JPQL obligatoria de tu prueba
    @GetMapping("/por-fecha")
    public ResponseEntity<List<ReservaResponseDTO>> obtenerPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(service.buscarDesdeFecha(fecha));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crear(@Valid @RequestBody ReservaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody ReservaRequestDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
