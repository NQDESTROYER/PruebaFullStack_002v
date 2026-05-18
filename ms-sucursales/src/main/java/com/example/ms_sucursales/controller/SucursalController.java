package com.example.ms_sucursales.controller;

import com.example.ms_sucursales.dto.SucursalRequestDTO;
import com.example.ms_sucursales.dto.SucursalResponseDTO;
import com.example.ms_sucursales.service.SucursalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sucursales")
@RequiredArgsConstructor
public class SucursalController {

    private final SucursalService sucursalService;

    @GetMapping
    public ResponseEntity<List<SucursalResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(sucursalService.listarTodas());
    }

    @GetMapping("/operativas")
    public ResponseEntity<List<SucursalResponseDTO>> obtenerOperativas() {
        return ResponseEntity.ok(sucursalService.listarOperativas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SucursalResponseDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(sucursalService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<SucursalResponseDTO> registrarNueva(@Valid @RequestBody SucursalRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sucursalService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SucursalResponseDTO> modificarExistente(
            @PathVariable Integer id,
            @Valid @RequestBody SucursalRequestDTO dto) {
        return ResponseEntity.ok(sucursalService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Integer id) {
        sucursalService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}