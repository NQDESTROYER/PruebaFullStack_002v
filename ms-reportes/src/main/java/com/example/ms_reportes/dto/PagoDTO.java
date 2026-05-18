package com.example.ms_reportes.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PagoDTO {
    private Integer id;
    private String codigoTransaccion;
    private BigDecimal monto;
    private LocalDateTime fechaPago;
    private String estadoPago;
}