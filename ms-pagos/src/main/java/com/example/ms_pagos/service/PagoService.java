package com.example.ms_pagos.service;

import com.example.ms_pagos.dto.PagoRequestDTO;
import com.example.ms_pagos.dto.PagoResponseDTO;
import java.util.List;

public interface PagoService {
    List<PagoResponseDTO> obtenerTodos();
    PagoResponseDTO obtenerPorId(Integer id);
    PagoResponseDTO registrarPago(PagoRequestDTO dto);
}