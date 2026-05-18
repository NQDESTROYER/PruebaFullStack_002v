package com.example.ms_reportes.service;

import com.example.ms_reportes.dto.ReporteConsolidadoDTO;
import com.example.ms_reportes.dto.ReporteDTO;
import com.example.ms_reportes.dto.ReporteRequestDTO;
import java.util.List;

public interface ReporteService {
    // Método para Feign Client
    ReporteConsolidadoDTO generarReporteConsolidadoCompleto();

    // Métodos CRUD obligatorios
    List<ReporteDTO> obtenerTodos();
    ReporteDTO obtenerPorId(Integer id);
    ReporteDTO crear(ReporteRequestDTO dto);
    ReporteDTO actualizar(Integer id, ReporteRequestDTO dto);
    void eliminar(Integer id);
}
