package com.example.ms_clientes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDTO {

    private Integer id;
    private String rut;
    private String nombreCompleto;
    private String email;
    private BigDecimal ingresoMensual;
    private boolean activo;
    private LocalDate fechaNacimiento;
    private List<DireccionResponseDTO> direcciones;
}