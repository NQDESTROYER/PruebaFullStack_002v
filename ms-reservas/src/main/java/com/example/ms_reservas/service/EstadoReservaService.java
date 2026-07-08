package com.example.ms_reservas.service;

import com.example.ms_reservas.dto.EstadoReservaRequestDTO;
import com.example.ms_reservas.dto.EstadoReservaResponseDTO;
import java.util.List;

public interface EstadoReservaService {
    List<EstadoReservaResponseDTO> listarTodos();
    EstadoReservaResponseDTO obtenerPorId(Integer id);
    EstadoReservaResponseDTO crear(EstadoReservaRequestDTO dto);
    EstadoReservaResponseDTO actualizar(Integer id, EstadoReservaRequestDTO dto);
    void eliminar(Integer id);
}
