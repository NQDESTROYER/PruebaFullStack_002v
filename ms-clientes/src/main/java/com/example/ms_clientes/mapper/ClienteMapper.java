package com.example.ms_clientes.mapper;

import com.example.ms_clientes.dto.ClientesRequestDTO;
import com.example.ms_clientes.entity.Cliente;
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
}
