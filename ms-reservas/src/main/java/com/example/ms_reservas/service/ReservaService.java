package com.example.ms_reservas.service;

import com.example.ms_reservas.dto.ReservaRequestDTO;
import com.example.ms_reservas.dto.ReservaResponseDTO;
import java.time.LocalDate;
import java.util.List;

public interface ReservaService {
    List<ReservaResponseDTO> listarTodas();
    List<ReservaResponseDTO> buscarDesdeFecha(LocalDate fecha);
    ReservaResponseDTO obtenerPorId(Integer id);
    ReservaResponseDTO crear(ReservaRequestDTO dto);
    ReservaResponseDTO actualizar(Integer id, ReservaRequestDTO dto);
    void eliminar(Integer id);
}
