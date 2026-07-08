package com.example.ms_empleados.service;

import com.example.ms_empleados.dto.EmpleadoRequestDTO;
import com.example.ms_empleados.dto.EmpleadoResponseDTO;

import java.util.List;

public interface IEmpleadoService {
    EmpleadoResponseDTO crear(EmpleadoRequestDTO request);

    List<EmpleadoResponseDTO> listarTodos();

    EmpleadoResponseDTO buscarPorId(Integer id);

    EmpleadoResponseDTO actualizar(Integer id, EmpleadoRequestDTO request);

    void eliminar(Integer id);
}
