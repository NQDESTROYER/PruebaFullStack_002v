package com.example.ms_empleados.mapper;

import com.example.ms_empleados.dto.EmpleadoRequestDTO;
import com.example.ms_empleados.dto.EmpleadoResponseDTO;
import com.example.ms_empleados.entity.Empleado;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoMapper {
    public Empleado toEntity(EmpleadoRequestDTO dto) {
        if (dto == null) return null;

        return Empleado.builder()
                .rut(dto.getRut())
                .nombreCompleto(dto.getNombreCompleto())
                .cargo(dto.getCargo())
                .sueldoBase(dto.getSueldoBase())
                .conContratoIndefinido(dto.getConContratoIndefinido())
                .fechaContratacion(dto.getFechaContratacion())
                .build();
    }

    public EmpleadoResponseDTO toResponseDTO(Empleado entity) {
        if (entity == null) return null;

        return EmpleadoResponseDTO.builder()
                .id(entity.getId())
                .rut(entity.getRut())
                .nombreCompleto(entity.getNombreCompleto())
                .cargo(entity.getCargo())
                .sueldoBase(entity.getSueldoBase())
                .conContratoIndefinido(entity.getConContratoIndefinido())
                .fechaContratacion(entity.getFechaContratacion())
                .build();
    }
}
