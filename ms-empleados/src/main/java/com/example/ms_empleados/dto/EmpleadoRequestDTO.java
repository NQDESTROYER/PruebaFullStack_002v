package com.example.ms_empleados.dto;

import jakarta.validation.constraints.*;
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
public class EmpleadoRequestDTO {

    @NotBlank(message = "El RUT es obligatorio")
    @Size(min = 9, max = 10, message = "El RUT debe tener entre 9 y 10 caracteres")
    private String rut;

    @NotBlank(message = "El nombre completo es obligatorio")
    private String nombreCompleto;

    @NotBlank(message = "El cargo es obligatorio")
    private String cargo;

    @NotNull(message = "El sueldo base no puede ser nulo")
    @Positive(message = "El sueldo base debe ser un número positivo")
    private BigDecimal sueldoBase;

    @NotNull(message = "Debe especificar si el contrato es indefinido")
    private Boolean conContratoIndefinido;

    @NotNull(message = "La fecha de contratación es obligatoria")
    @PastOrPresent(message = "La fecha de contratación no puede ser una fecha futura")
    private LocalDate fechaContratacion;

    @NotNull(message = "Debe especificar si el empleado está activo")
    private Boolean activo;
}