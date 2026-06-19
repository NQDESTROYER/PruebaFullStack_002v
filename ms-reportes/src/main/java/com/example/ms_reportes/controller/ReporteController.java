package com.example.ms_reportes.controller;

import com.example.ms_reportes.assemblers.ReporteModelAssembler;
import com.example.ms_reportes.dto.ReporteConsolidadoDTO;
import com.example.ms_reportes.dto.ReporteRequestDTO;
import com.example.ms_reportes.dto.ReporteResponseDTO;
import com.example.ms_reportes.service.ReporteService;
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
@RequestMapping("/api/v1/reportes")
@Tag(name = "Reportes", description = "Gestión de Reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;
    private final ReporteModelAssembler assembler;

    @GetMapping("/consolidado")
    @Operation(summary = "Obtener reporte consolidado")
    @ApiResponse(responseCode = "200", description = "Reporte consolidado")
    public ResponseEntity<ReporteConsolidadoDTO> obtenerConsolidadoCompleto() {
        return ResponseEntity.ok(reporteService.generarReporteConsolidadoCompleto());
    }

    // --- MÉTODOS CRUD ---
    @GetMapping
    @Operation(summary = "Listar todos los reportes")
    @ApiResponse(responseCode = "200", description = "Lista de reportes")
    public ResponseEntity<CollectionModel<EntityModel<ReporteResponseDTO>>> listarTodos() {
        List<EntityModel<ReporteResponseDTO>> models = reporteService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(models,
                linkTo(methodOn(ReporteController.class).listarTodos()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar reporte por ID")
    @ApiResponse(responseCode = "200", description = "Reporte encontrado")
    @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    public ResponseEntity<EntityModel<ReporteResponseDTO>> buscarPorId(@PathVariable Integer id) {
        ReporteResponseDTO dto = reporteService.obtenerPorId(id);
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @PostMapping
    @Operation(summary = "Crear reporte")
    @ApiResponse(responseCode = "201", description = "Reporte creado")
    @ApiResponse(responseCode = "400", description = "Error de validación")
    public ResponseEntity<EntityModel<ReporteResponseDTO>> crearReporte(@Valid @RequestBody ReporteRequestDTO dto) {
        ReporteResponseDTO created = reporteService.crear(dto);
        return new ResponseEntity<>(assembler.toModel(created), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reporte")
    @ApiResponse(responseCode = "200", description = "Reporte actualizado")
    @ApiResponse(responseCode = "400", description = "Error de validación")
    @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    public ResponseEntity<EntityModel<ReporteResponseDTO>> actualizarReporte(@PathVariable Integer id, @Valid @RequestBody ReporteRequestDTO dto) {
        ReporteResponseDTO updated = reporteService.actualizar(id, dto);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reporte")
    @ApiResponse(responseCode = "204", description = "Reporte eliminado")
    @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    public ResponseEntity<Void> eliminarReporte(@PathVariable Integer id) {
        reporteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
