package com.example.ms_reportes.mapper;

import com.example.ms_reportes.dto.ReporteDTO;
import com.example.ms_reportes.dto.ReporteRequestDTO;
import com.example.ms_.model.Reporte;
import org.springframework.stereotype.Component;

@Component
public class ReporteMapper {

    public ReporteDTO toDTO(Reporte entity) {
        if (entity == null) return null;
        ReporteDTO dto = new ReporteDTO();
        dto.setId(entity.getId());
        dto.setNombreReporte(entity.getNombreReporte());
        dto.setCategoria(entity.getCategoria());
        dto.setTotalRegistros(entity.getTotalRegistros());
        dto.setDescription(entity.getDescription());
        dto.setFechaEmision(entity.getFechaEmision());
        dto.setProcesado(entity.getProcesado());
        return dto;
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

