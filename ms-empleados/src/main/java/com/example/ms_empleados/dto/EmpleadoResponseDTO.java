package com.example.ms_empleados.dto;

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
public class EmpleadoResponseDTO {

    private Integer id;
    private String rut;
    private String nombreCompleto;
    private String cargo;
    private BigDecimal sueldoBase;
    private Boolean conContratoIndefinido;
    private LocalDate fechaContratacion;

}
