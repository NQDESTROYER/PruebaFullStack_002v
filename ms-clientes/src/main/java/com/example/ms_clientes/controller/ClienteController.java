package com.example.ms_clientes.controller;

import com.example.ms_clientes.dto.ClienteResponseDTO;
import com.example.ms_clientes.dto.ClientesRequestDTO;
import com.example.ms_clientes.service.IClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Gestión de Clientes")
public class ClienteController {
    private final IClienteService clienteService;

    // 1. CREAR CLIENTE (POST)
    @PostMapping
    @Operation(summary = "Crear cliente", description = "Crea un nuevo cliente")
    @ApiResponse(responseCode = "201", description = "Cliente creado")
    @ApiResponse(responseCode = "400", description = "Error de validación")
    public ResponseEntity<ClienteResponseDTO> crear(@Valid @RequestBody ClientesRequestDTO dto) {
        return new ResponseEntity<>(clienteService.crear(dto), HttpStatus.CREATED);
    }

    // 2. BUSCAR POR ID (GET)
    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID")
    @ApiResponse(responseCode = "200", description = "Cliente encontrado")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    public ResponseEntity<EntityModel<ClienteResponseDTO>> buscarPorId(@PathVariable Integer id) {
        ClienteResponseDTO dto = clienteService.buscarPorId(id);
        EntityModel<ClienteResponseDTO> model = EntityModel.of(dto);
        model.add(linkTo(methodOn(ClienteController.class).buscarPorId(id)).withSelfRel());
        return ResponseEntity.ok(model);
    }

    // 3. LISTAR TODOS (GET)
    @GetMapping
    @Operation(summary = "Listar todos los clientes")
    @ApiResponse(responseCode = "200", description = "Lista de clientes")
    public ResponseEntity<CollectionModel<EntityModel<ClienteResponseDTO>>> listarTodos() {
        List<ClienteResponseDTO> dtos = clienteService.listarTodos();
        
        List<EntityModel<ClienteResponseDTO>> models = dtos.stream()
                .map(dto -> EntityModel.of(dto, 
                        linkTo(methodOn(ClienteController.class).buscarPorId(dto.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(models, 
                linkTo(methodOn(ClienteController.class).listarTodos()).withSelfRel()));
    }

    // 4. ACTUALIZAR CLIENTE (PUT)
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente")
    @ApiResponse(responseCode = "200", description = "Cliente actualizado")
    @ApiResponse(responseCode = "400", description = "Error de validación")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    public ResponseEntity<ClienteResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody ClientesRequestDTO dto) {
        return ResponseEntity.ok(clienteService.actualizar(id, dto));
    }

    // 5. ELIMINAR CLIENTE (DELETE)
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente")
    @ApiResponse(responseCode = "204", description = "Cliente eliminado")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
