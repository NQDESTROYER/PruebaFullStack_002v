package com.example.ms_pagos.service;

import com.example.ms_pagos.dto.PagoRequestDTO;
import com.example.ms_pagos.dto.PagoResponseDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface PagoService {

    List<PagoResponseDTO> obtenerTodos();

    PagoResponseDTO obtenerPorId(Integer id);

    PagoResponseDTO registrarPago(PagoRequestDTO dto);

    // Nuevo método para la paginación
    Page<PagoResponseDTO> obtenerPagosPorRango(BigDecimal min, BigDecimal max, int page, int size);
}