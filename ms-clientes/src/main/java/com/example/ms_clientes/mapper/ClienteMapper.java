package com.example.ms_clientes.mapper;

import com.example.ms_clientes.dto.ClienteResponseDTO;
import com.example.ms_clientes.dto.ClientesRequestDTO;
import com.example.ms_clientes.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClientesRequestDTO dto){
        if (dto == null){
            return null;
        }
        return Cliente.builder()
                .rut(dto.getRut())
                .nombreCompleto(dto.getNombreCompleto())
                .email(dto.getEmail()) //
                .ingresoMensual(dto.getIngresoMensual())
                .activo(dto.getActivo())
                .fechaNacimiento(dto.getFechaNacimiento())
                .build();
    }

    public ClienteResponseDTO toResponseDTO(Cliente entity){
        if (entity==null){
            return null;
        }

        return ClienteResponseDTO.builder()
                .id(entity.getId())
                .rut(entity.getRut())
                .nombreCompleto(entity.getNombreCompleto())
                .email(entity.getEmail()) //
                .ingresoMensual(entity.getIngresoMensual())
                .activo(entity.isActivo())
                .fechaNacimiento(entity.getFechaNacimiento())
                .build();
    }

    public void actualizarEntidad(ClientesRequestDTO dto, Cliente entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setRut(dto.getRut());
        entity.setNombreCompleto(dto.getNombreCompleto());
        entity.setEmail(dto.getEmail()); //
        entity.setIngresoMensual(dto.getIngresoMensual());
        entity.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        entity.setFechaNacimiento(dto.getFechaNacimiento());
    }
}