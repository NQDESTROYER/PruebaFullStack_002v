package com.example.ms_vehiculos.service;

import com.example.ms_vehiculos.dto.VehiculoRequestDTO;
import com.example.ms_vehiculos.dto.VehiculosResponseDTO;
import com.example.ms_vehiculos.model.Categoria;

import java.util.List;

public interface IVehiculoService {
    VehiculosResponseDTO crear(VehiculoRequestDTO dto);
    List<VehiculosResponseDTO> listarTodos();
    VehiculosResponseDTO buscarPorId(Integer id);
    VehiculosResponseDTO actualizar(Integer id, VehiculoRequestDTO dto);
    void eliminar(Integer id);

    // ===================================================================
    // CONTRATO LEGAL ADICIONAL PARA LOS SUB-RECURSOS
    // ===================================================================
    Categoria obtenerCategoriaPorVehiculoId(Integer id);
    Categoria actualizarCategoria(Integer id, Integer nuevaCategoriaId);
}