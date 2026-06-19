package com.example.ms_sucursales.service;

import com.example.ms_sucursales.dto.SucursalRequestDTO;
import com.example.ms_sucursales.dto.SucursalResponseDTO;
import java.util.List;

public interface SucursalService {
    List<SucursalResponseDTO> listarTodas();
    List<SucursalResponseDTO> listarOperativas();
    SucursalResponseDTO obtenerPorId(Integer id);
    SucursalResponseDTO crear(SucursalRequestDTO dto);
    SucursalResponseDTO actualizar(Integer id, SucursalRequestDTO dto);
    void eliminar(Integer id);
}

