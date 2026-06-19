package com.example.ms_vehiculos.dto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehiculoRequestDTO {
    @NotBlank(message = "La patente es obligatoria")
    @Size(min = 6, max = 10, message = "La patente debe tener entre 6 y 10 caracteres")
    private String patente;

    @NotBlank(message = "La marca es obligatoria")
    @Size(max = 50, message = "La marca no puede superar los 50 caracteres")
    private String marca;

    @NotNull(message = "El precio diario es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio diario debe ser mayor a 0")
    private Double precioDiario;

    // Al ser un booleano primitivo no puede ser null, por defecto es false, pero validamos que venga el campo
    @NotNull(message = "El estado de disponibilidad es obligatorio")
    private Boolean disponible;

    @NotNull(message = "La fecha de ingreso es obligatoria")
    @PastOrPresent(message = "La fecha de ingreso no puede ser una fecha futura")
    private LocalDate fechaIngreso;

}
