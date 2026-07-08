package com.example.ms_pagos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoResponseDTO {
    private Integer id;
    private String codigoTransaccion;
    private Integer reservaId;
    private BigDecimal monto;
    private LocalDateTime fechaPago;
    private String estadoPago;
    private Integer metodoPagoId;
    private String metodoPagoNombre;
}
