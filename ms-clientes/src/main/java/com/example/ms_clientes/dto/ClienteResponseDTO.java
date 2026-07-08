package com.example.ms_clientes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteResponseDTO {
    private Integer id;
    private String rut;
    private String nombreCompleto;
    private BigDecimal ingresoMensual;
    private Boolean activo;
    private LocalDate fechaNacimiento;
    private String email;

}
