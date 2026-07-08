package com.example.ms_vehiculos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class VehiculosResponseDTO {

    private Integer id;//-- el cliente necesita ver su id
    private String patente;
    private String marca;
    private Double precioDiario;
    private boolean disponible;
    private LocalDate fechaIngreso;
}
