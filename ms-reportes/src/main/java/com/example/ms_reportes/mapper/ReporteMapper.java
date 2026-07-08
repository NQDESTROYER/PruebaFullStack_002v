package com.example.ms_reportes.mapper;

import com.example.ms_reportes.dto.ReporteResponseDTO;
import com.example.ms_reportes.dto.ReporteRequestDTO;
import com.example.ms_reportes.entity.Reporte;
import org.springframework.stereotype.Component;

@Component
public class ReporteMapper {

    public ReporteResponseDTO toResponseDTO(Reporte entity) {
        if (entity == null) return null;
        return ReporteResponseDTO.builder()
                .id(entity.getId())
                .nombreReporte(entity.getNombreReporte())
                .categoria(entity.getCategoria())
                .totalRegistros(entity.getTotalRegistros())
                .description(entity.getDescription())
                .fechaEmision(entity.getFechaEmision())
                .procesado(entity.getProcesado())
                .build();
    }

    public Reporte toEntity(ReporteRequestDTO dto) {
        if (dto == null) return null;
        Reporte entity = new Reporte();
        entity.setNombreReporte(dto.getNombreReporte());
        entity.setCategoria(dto.getCategoria());
        entity.setTotalRegistros(dto.getTotalRegistros());
        entity.setDescription(dto.getDescription());
        entity.setFechaEmision(dto.getFechaEmision());
        entity.setProcesado(true);
        return entity;
    }
}
