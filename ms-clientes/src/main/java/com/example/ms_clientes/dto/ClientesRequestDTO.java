package com.example.ms_clientes.dto;

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
public class ClientesRequestDTO {
    // @NotBlank: Evita que el texto sea nulo, esté vacío o tenga puros espacios.
    // @Size: Restringe el largo del texto a un rango mínimo y máximo permitido.
    // @Positive: Valida que el número ingresado sea estrictamente mayor a 0 (evita montos negativos o en cero).
    // @Past: Exige que la fecha ingresada sea anterior al día de hoy (evita fechas futuras imposibles).

    @NotBlank(message = "El rut no puede estar vacio")
    @Size(min= 9, max = 10, message = "El rut debe tener entre 9 y 10 caracteres")
    private String rut;

    @NotBlank(message = "El nombre completo no puede estar vacio")
    private String nombreCompleto;

    @NotNull(message = "El ingreso mensual es obligatorio")
    @Positive(message = "El ingreso mensual debe ser un numero positivo")
    private BigDecimal ingresoMensual;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;

    @NotNull(message = "La fecha de nacimiento no puede ser nula")
    @Past(message = "La fecha de nacimiento debe ser una fecha del pasado")
    private LocalDate fechaNacimiento;


}
