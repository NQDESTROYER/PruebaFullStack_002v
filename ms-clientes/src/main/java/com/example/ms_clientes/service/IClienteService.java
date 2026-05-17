package com.example.ms_clientes.service;

import com.example.ms_clientes.dto.ClienteResponseDTO;
import com.example.ms_clientes.dto.ClientesRequestDTO;

import java.util.List;

//Este archivo solo define qué operaciones tendrá nuestro CRUD. Es el contrato obligatorio
public interface IClienteService {
    ClienteResponseDTO crear(ClientesRequestDTO dto);
    ClienteResponseDTO buscarPorId(Integer id);
    List<ClienteResponseDTO> listarTodos();
    ClienteResponseDTO actualizar(Integer id, ClientesRequestDTO dto);
    void eliminar(Integer id);
}
