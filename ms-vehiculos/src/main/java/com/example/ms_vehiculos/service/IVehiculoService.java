package com.example.ms_vehiculos.service;

import com.example.ms_vehiculos.dto.VehiculoRequestDTO;
import com.example.ms_vehiculos.dto.VehiculosResponseDTO;

import java.util.List;

public interface IVehiculoService {
    VehiculosResponseDTO crear(VehiculoRequestDTO dto);
    List<VehiculosResponseDTO> listarTodos();
    VehiculosResponseDTO buscarPorId(Integer id);
    VehiculosResponseDTO actualizar(Integer id, VehiculoRequestDTO dto);
    void eliminar(Integer id);
    //lo que tenemos aca es una interfaz, es como un contrato legal
    //se define que acciones esta obligado a cumplir el sistema
}
