package com.example.ms_reportes.service;

import com.example.ms_reportes.dto.ReporteConsolidadoDTO;
import com.example.ms_reportes.dto.ReporteResponseDTO;
import com.example.ms_reportes.dto.ReporteRequestDTO;
import java.util.List;

public interface ReporteService {
    // Método para Feign Client
    ReporteConsolidadoDTO generarReporteConsolidadoCompleto();

    // Métodos CRUD obligatorios
    List<ReporteResponseDTO> obtenerTodos();
    ReporteResponseDTO obtenerPorId(Integer id);
    ReporteResponseDTO crear(ReporteRequestDTO dto);
    ReporteResponseDTO actualizar(Integer id, ReporteRequestDTO dto);
    void eliminar(Integer id);
}
