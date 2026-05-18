package com.example.ms_reservas.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ReservaResponseDTO {
    private Integer id;
    private String codigoReserva;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private BigDecimal montoTotal;
    private Boolean seguroIncluido;
    private Integer estadoId;
    private String nombreEstado; // Añadido para que el cliente vea el nombre en vez de solo el ID
    private Integer clienteId;
    private Integer vehiculoId;
}
