package com.example.ms_clientes.controller;

import com.example.ms_clientes.dto.ClienteResponseDTO;
import com.example.ms_clientes.dto.ClientesRequestDTO;
import com.example.ms_clientes.service.IClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {
    private final IClienteService clienteService;

    // 1. CREAR CLIENTE (POST)
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> crear(@Valid @RequestBody ClientesRequestDTO dto) {
        return new ResponseEntity<>(clienteService.crear(dto), HttpStatus.CREATED); // Devuelve un estado 201 Created
    }

    // 2. BUSCAR POR ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(clienteService.buscarPorId(id)); // Devuelve 200 OK
    }

    // 3. LISTAR TODOS (GET)
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos()); // Devuelve 200 OK
    }

    // 4. ACTUALIZAR CLIENTE (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody ClientesRequestDTO dto) {
        return ResponseEntity.ok(clienteService.actualizar(id, dto)); // Devuelve 200 OK
    }

    // 5. ELIMINAR CLIENTE (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build(); // Devuelve un estado 244/204 No Content limpio
    }

}
