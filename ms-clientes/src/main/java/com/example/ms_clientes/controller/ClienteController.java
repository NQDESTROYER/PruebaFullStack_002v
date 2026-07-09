package com.example.ms_clientes.controller;

import com.example.ms_clientes.dto.ClienteResponseDTO;
import com.example.ms_clientes.dto.ClientesRequestDTO;
import com.example.ms_clientes.dto.DireccionRequestDTO;
import com.example.ms_clientes.dto.DireccionResponseDTO;
import com.example.ms_clientes.service.IClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "API de gestión de clientes y sus direcciones")
public class ClienteController {

    private final IClienteService clienteService;

    // 1. OBTENER TODOS LOS CLIENTES (Sincronizado con listarTodos)
    @GetMapping
    @Operation(summary = "Obtener todos los clientes", description = "Devuelve una lista con todos los clientes registrados")
    @ApiResponse(responseCode = "200", description = "Lista obtenida con éxito")
    public ResponseEntity<List<ClienteResponseDTO>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    // 2. OBTENER UN CLIENTE POR ID (Sincronizado con buscarPorId)
    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID", description = "Devuelve los detalles de un cliente específico")
    @ApiResponse(responseCode = "200", description = "Cliente encontrado")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    // 3. CREAR UN NUEVO CLIENTE
    @PostMapping
    @Operation(summary = "Crear un nuevo cliente", description = "Registra un cliente en el sistema")
    @ApiResponse(responseCode = "201", description = "Cliente creado con éxito")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    public ResponseEntity<ClienteResponseDTO> crear(@Valid @RequestBody ClientesRequestDTO clientesRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.crear(clientesRequestDTO));
    }

    // 4. ACTUALIZAR UN CLIENTE EXISTENTE
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente", description = "Modifica los datos de un cliente existente")
    @ApiResponse(responseCode = "200", description = "Cliente actualizado con éxito")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    public ResponseEntity<ClienteResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody ClientesRequestDTO clientesRequestDTO) {
        return ResponseEntity.ok(clienteService.actualizar(id, clientesRequestDTO));
    }

    // 5. ELIMINAR UN CLIENTE
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente", description = "Borra un cliente del sistema por su ID")
    @ApiResponse(responseCode = "204", description = "Cliente eliminado con éxito (No Content)")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ===================================================================
    // SUB-RECURSOS: DIRECCIÓN DEL CLIENTE
    // ===================================================================

    // 6. OBTENER DIRECCIÓN DE UN CLIENTE (GET)
    @GetMapping("/{id}/direccion")
    @Operation(summary = "Obtener dirección del cliente", description = "Devuelve la dirección principal asociada a un cliente específico")
    @ApiResponse(responseCode = "200", description = "Dirección encontrada")
    @ApiResponse(responseCode = "404", description = "Cliente o dirección no encontrada")
    public ResponseEntity<DireccionResponseDTO> obtenerDireccionPorClienteId(@PathVariable Integer id) {
        DireccionResponseDTO direccion = clienteService.obtenerDireccionPorClienteId(id);
        return ResponseEntity.ok(direccion);
    }

    // 7. ACTUALIZAR/AGREGAR DIRECCIÓN A UN CLIENTE (PUT)
    @PutMapping("/{id}/direccion")
    @Operation(summary = "Actualizar dirección del cliente", description = "Actualiza o asigna la dirección principal de un cliente")
    @ApiResponse(responseCode = "200", description = "Dirección actualizada")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    public ResponseEntity<DireccionResponseDTO> actualizarDireccion(
            @PathVariable Integer id,
            @Valid @RequestBody DireccionRequestDTO direccionRequestDTO) {
        DireccionResponseDTO actualizada = clienteService.actualizarDireccion(id, direccionRequestDTO);
        return ResponseEntity.ok(actualizada);
    }
}