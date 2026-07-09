package com.example.ms_clientes.service;

import com.example.ms_clientes.dto.ClienteResponseDTO;
import com.example.ms_clientes.dto.ClientesRequestDTO;
import com.example.ms_clientes.dto.DireccionRequestDTO;
import com.example.ms_clientes.dto.DireccionResponseDTO;

import java.util.List;

// Este archivo solo define qué operaciones tendrá nuestro CRUD. Es el contrato obligatorio
public interface IClienteService {
    ClienteResponseDTO crear(ClientesRequestDTO dto);
    ClienteResponseDTO buscarPorId(Integer id);
    List<ClienteResponseDTO> listarTodos();
    ClienteResponseDTO actualizar(Integer id, ClientesRequestDTO dto);
    void eliminar(Integer id);

    // NUEVOS SUB-RECURSOS EN SINGULAR SEGÚN REQUERIMIENTO
    DireccionResponseDTO obtenerDireccionPorClienteId(Integer id);
    DireccionResponseDTO actualizarDireccion(Integer id, DireccionRequestDTO direccionRequestDTO);
}