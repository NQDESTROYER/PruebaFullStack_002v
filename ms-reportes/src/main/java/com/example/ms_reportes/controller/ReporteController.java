package com.example.ms_reportes.controller;

import com.example.ms_reportes.dto.ReporteConsolidadoDTO;
import com.example.ms_reportes.dto.ReporteDTO;
import com.example.ms_reportes.dto.ReporteRequestDTO;
import com.example.ms_reportes.service.ReporteService;
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
@RequestMapping("/api/v1/reportes")
@Tag(name = "Reportes", description = "Gestión de Reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/consolidado")
    @Operation(summary = "Obtener reporte consolidado")
    @ApiResponse(responseCode = "200", description = "Reporte consolidado")
    public ResponseEntity<ReporteConsolidadoDTO> obtenerConsolidadoCompleto() {
        return ResponseEntity.ok(reporteService.generarReporteConsolidadoCompleto());
    }

    // --- MÉTODOS CRUD EXIGIDOS POR RÚBRICA ---
    @GetMapping
    @Operation(summary = "Listar todos los reportes")
    @ApiResponse(responseCode = "200", description = "Lista de reportes")
    public ResponseEntity<CollectionModel<EntityModel<ReporteDTO>>> listarTodos() {
        List<ReporteDTO> dtos = reporteService.obtenerTodos();

        List<EntityModel<ReporteDTO>> models = dtos.stream()
                .map(dto -> EntityModel.of(dto,
                        linkTo(methodOn(ReporteController.class).buscarPorId(dto.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(models,
                linkTo(methodOn(ReporteController.class).listarTodos()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar reporte por ID")
    @ApiResponse(responseCode = "200", description = "Reporte encontrado")
    @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    public ResponseEntity<EntityModel<ReporteDTO>> buscarPorId(@PathVariable Integer id) {
        ReporteDTO dto = reporteService.obtenerPorId(id);
        EntityModel<ReporteDTO> model = EntityModel.of(dto);
        model.add(linkTo(methodOn(ReporteController.class).buscarPorId(id)).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @PostMapping
    @Operation(summary = "Crear reporte")
    @ApiResponse(responseCode = "201", description = "Reporte creado")
    @ApiResponse(responseCode = "400", description = "Error de validación")
    public ResponseEntity<ReporteDTO> crearReporte(@Valid @RequestBody ReporteRequestDTO dto) {
        return new ResponseEntity<>(reporteService.crear(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reporte")
    @ApiResponse(responseCode = "200", description = "Reporte actualizado")
    @ApiResponse(responseCode = "400", description = "Error de validación")
    @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    public ResponseEntity<ReporteDTO> actualizarReporte(@PathVariable Integer id, @Valid @RequestBody ReporteRequestDTO dto) {
        return ResponseEntity.ok(reporteService.actualizar(id, dto));
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
