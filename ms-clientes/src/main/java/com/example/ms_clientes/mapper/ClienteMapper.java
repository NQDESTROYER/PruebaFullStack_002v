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
                .id(entity.getId()) // Aquí sí mapeamos el ID generado por la BD
                .rut(entity.getRut())
                .nombreCompleto(entity.getNombreCompleto())
                .ingresoMensual(entity.getIngresoMensual())
                .activo(entity.isActivo())
                .fechaNacimiento(entity.getFechaNacimiento())
                .build();
    }

}
