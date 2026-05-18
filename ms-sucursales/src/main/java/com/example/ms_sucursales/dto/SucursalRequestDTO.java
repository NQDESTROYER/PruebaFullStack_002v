package com.example.ms_sucursales.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SucursalRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "Debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotNull(message = "La capacidad es obligatoria")
    @Positive(message = "Debe ser un número positivo")
    @Min(value = 5, message = "Mínimo 5 vehículos")
    private Integer capacidadAutos;

    private boolean operativa;

    @NotNull(message = "La fecha es obligatoria")
    @PastOrPresent(message = "No puede ser una fecha futura")
    private LocalDateTime fechaApertura;

    @NotNull(message = "El ID de la región es obligatorio")
    private Integer regionId;
}
