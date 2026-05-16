package com.example.ms_vehiculos.mapper;

import com.example.ms_vehiculos.dto.VehiculoRequestDTO;
import com.example.ms_vehiculos.dto.VehiculosResponseDTO;
import com.example.ms_vehiculos.entity.Vehiculo;
import org.springframework.stereotype.Component;

@Component//--esta clase es un componente reutilizable
public class VehiculoMapper {
    public Vehiculo toEntity(VehiculoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return Vehiculo.builder()
                .patente(dto.getPatente())
                .marca(dto.getMarca())
                .precioDiario(dto.getPrecioDiario())
                .disponible(dto.getDisponible())
                .fechaIngreso(dto.getFechaIngreso())
                .build();
        }

    public VehiculosResponseDTO toResponseDTO(Vehiculo entity) {
        if (entity == null) {
            return null;
        }

        return VehiculosResponseDTO.builder()
                .id(entity.getId()) // Aquí sí pasamos el ID
                .patente(entity.getPatente())
                .marca(entity.getMarca())
                .precioDiario(entity.getPrecioDiario())
                .disponible(entity.isDisponible())
                .fechaIngreso(entity.getFechaIngreso())
                .build();
        }
    }

