package com.example.ms_sucursales.mapper;

import com.example.ms_sucursales.dto.SucursalRequestDTO;
import com.example.ms_sucursales.dto.SucursalResponseDTO;
import com.example.ms_sucursales.model.Sucursal;
import org.springframework.stereotype.Component;

@Component
public class SucursalMapper {

    public Sucursal toEntity(SucursalRequestDTO dto) {
        if (dto == null) return null;
        Sucursal sucursal = new Sucursal();
        sucursal.setNombre(dto.getNombre());
        sucursal.setDireccion(dto.getDireccion());
        sucursal.setCapacidadAutos(dto.getCapacidadAutos());
        sucursal.setOperativa(dto.isOperativa());
        sucursal.setFechaApertura(dto.getFechaApertura());
        return sucursal;
    }

    public SucursalResponseDTO toDto(Sucursal entity) {
        if (entity == null) return null;
        SucursalResponseDTO dto = new SucursalResponseDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDireccion(entity.getDireccion());
        dto.setCapacidadAutos(entity.getCapacidadAutos());
        dto.setOperativa(entity.isOperativa());
        dto.setFechaApertura(entity.getFechaApertura());
        if (entity.getRegion() != null) {
            dto.setRegionId(entity.getRegion().getId());
        }
        return dto;
    }
}


