package com.example.ms_vehiculos.controller;

import com.example.ms_vehiculos.dto.VehiculoRequestDTO;
import com.example.ms_vehiculos.dto.VehiculosResponseDTO;
import com.example.ms_vehiculos.service.IVehiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/vehiculos")
@RestController
public class VehiculoController {

    private final IVehiculoService vehiculoService;

    @PostMapping
    public ResponseEntity<VehiculosResponseDTO> crear(@Valid @RequestBody VehiculoRequestDTO dto){
        VehiculosResponseDTO nuevoVehiculo = vehiculoService.crear(dto);
        return new ResponseEntity<>(nuevoVehiculo, HttpStatus.CREATED); // Devuelve un estado 201 Created
    }

    @GetMapping
    public ResponseEntity<List<VehiculosResponseDTO>> listarTodos() {
        List<VehiculosResponseDTO> lista = vehiculoService.listarTodos();
        return ResponseEntity.ok(lista); // Devuelve un estado 200 OK con la lista
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculosResponseDTO> buscarPorId(@PathVariable Integer id) {
        // @PathVariable captura el número que pongas al final de la URL
        VehiculosResponseDTO vehiculo = vehiculoService.buscarPorId(id);
        return ResponseEntity.ok(vehiculo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehiculosResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody VehiculoRequestDTO dto) {
        VehiculosResponseDTO vehiculoActualizado = vehiculoService.actualizar(id, dto);
        return ResponseEntity.ok(vehiculoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        vehiculoService.eliminar(id);
        return ResponseEntity.noContent().build(); // Devuelve un estado 204 No Content (indica éxito sin cuerpo)
    }
}
