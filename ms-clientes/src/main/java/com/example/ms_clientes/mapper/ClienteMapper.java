package com.example.ms_clientes.mapper;

import com.example.ms_clientes.dto.ClienteResponseDTO;
import com.example.ms_clientes.dto.ClientesRequestDTO;
import com.example.ms_clientes.dto.DireccionResponseDTO; // Asegúrate de crear este DTO
import com.example.ms_clientes.model.Cliente;
import com.example.ms_clientes.model.Direccion;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClientesRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Cliente cliente = Cliente.builder()
                .rut(dto.getRut())
                .nombreCompleto(dto.getNombreCompleto())
                .email(dto.getEmail())
                .ingresoMensual(dto.getIngresoMensual())
                .activo(dto.getActivo())
                .fechaNacimiento(dto.getFechaNacimiento())
                // No inicializamos las direcciones aquí en el builder directo
                .build();

        // Usamos el método helper para vincular cada dirección al cliente y evitar nulos
        if (dto.getDirecciones() != null) {
            dto.getDirecciones().forEach(dirDto -> {
                Direccion direccion = Direccion.builder()
                        .calle(dirDto.getCalle())
                        .numero(dirDto.getNumero())
                        .ciudad(dirDto.getCiudad())
                        .principal(dirDto.isPrincipal())
                        .fechaRegistro(dirDto.getFechaRegistro())
                        .build();
                cliente.addDireccion(direccion);
            });
        }

        return cliente;
    }

    public ClienteResponseDTO toResponseDTO(Cliente entity) {
        if (entity == null) {
            return null;
        }

        return ClienteResponseDTO.builder()
                .id(entity.getId())
                .rut(entity.getRut())
                .nombreCompleto(entity.getNombreCompleto())
                .email(entity.getEmail())
                .ingresoMensual(entity.getIngresoMensual())
                .activo(entity.isActivo())
                .fechaNacimiento(entity.getFechaNacimiento())
                // Convertimos la lista de Entidades a una lista de Response DTOs
                .direcciones(entity.getDirecciones() != null ?
                        entity.getDirecciones().stream().map(dir -> DireccionResponseDTO.builder()
                                .id(dir.getId())
                                .calle(dir.getCalle())
                                .numero(dir.getNumero())
                                .ciudad(dir.getCiudad())
                                .principal(dir.isPrincipal())
                                .fechaRegistro(dir.getFechaRegistro())
                                .build()
                        ).collect(Collectors.toList()) : new ArrayList<>())
                .build();
    }

    public void actualizarEntidad(ClientesRequestDTO dto, Cliente entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setRut(dto.getRut());
        entity.setNombreCompleto(dto.getNombreCompleto());
        entity.setEmail(dto.getEmail());
        entity.setIngresoMensual(dto.getIngresoMensual());
        entity.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        entity.setFechaNacimiento(dto.getFechaNacimiento());

        // Actualización de direcciones: Limpiamos la lista actual y agregamos las nuevas.
        // Gracias al orphanRemoval = true de tu entidad, Hibernate borrará las antiguas de la BD automáticamente.
        if (dto.getDirecciones() != null) {
            entity.getDirecciones().clear();
            dto.getDirecciones().forEach(dirDto -> {
                Direccion nuevaDireccion = Direccion.builder()
                        .calle(dirDto.getCalle())
                        .numero(dirDto.getNumero())
                        .ciudad(dirDto.getCiudad())
                        .principal(dirDto.isPrincipal())
                        .fechaRegistro(dirDto.getFechaRegistro())
                        .build();
                entity.addDireccion(nuevaDireccion);
            });
        }
    }
}