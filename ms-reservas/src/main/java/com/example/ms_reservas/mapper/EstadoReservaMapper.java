package com.example.ms_reservas.mapper;

import com.example.ms_reservas.dto.EstadoReservaRequestDTO;
import com.example.ms_reservas.dto.EstadoReservaResponseDTO;
import com.example.ms_reservas.model.EstadoReserva;
import org.springframework.stereotype.Component;

@Component
public class EstadoReservaMapper {
    public EstadoReserva toEntity(EstadoReservaRequestDTO dto) {
        if (dto == null) return null;
        EstadoReserva estado = new EstadoReserva();
        estado.setNombreEstado(dto.getNombreEstado());
        estado.setDescripcion(dto.getDescripcion());
        estado.setPermiteModificacion(dto.getPermiteModificacion());
        estado.setNivelPrioridad(dto.getNivelPrioridad());
        estado.setFechaCreacion(dto.getFechaCreacion());
        return estado;
    }

    public EstadoReservaResponseDTO toDto(EstadoReserva entity) {
        if (entity == null) return null;
        EstadoReservaResponseDTO dto = new EstadoReservaResponseDTO();
        dto.setId(entity.getId());
        dto.setNombreEstado(entity.getNombreEstado());
        dto.setDescripcion(entity.getDescripcion());
        dto.setPermiteModificacion(entity.getPermiteModificacion());
        dto.setNivelPrioridad(entity.getNivelPrioridad());
        dto.setFechaCreacion(entity.getFechaCreacion());
        return dto;
    }
}
