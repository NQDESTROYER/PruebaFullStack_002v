package com.example.ms_reportes.controller;

import com.example.ms_reportes.dto.ReporteConsolidadoDTO;
import com.example.ms_reportes.dto.ReporteDTO;
import com.example.ms_reportes.dto.ReporteRequestDTO;
import com.example.ms_reportes.service.ReporteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/consolidado")
    public ResponseEntity<ReporteConsolidadoDTO> obtenerConsolidadoCompleto() {
        return ResponseEntity.ok(reporteService.generarReporteConsolidadoCompleto());
    }

    // --- MÉTODOS CRUD EXIGIDOS POR RÚBRICA ---
    @GetMapping
    public ResponseEntity<List<ReporteDTO>> listarTodos() {
        return ResponseEntity.ok(reporteService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(reporteService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ReporteDTO> crearReporte(@Valid @RequestBody ReporteRequestDTO dto) {
        return new ResponseEntity<>(reporteService.crear(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReporteDTO> actualizarReporte(@PathVariable Integer id, @Valid @RequestBody ReporteRequestDTO dto) {
        return ResponseEntity.ok(reporteService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReporte(@PathVariable Integer id) {
        reporteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
