package com.example.ms_reservas.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class EstadoReservaRequestDTO {
    @NotBlank(message = "El nombre del estado es obligatorio")
    @Size(min = 3, max = 50, message = "Debe tener entre 3 y 50 caracteres")
    private String nombreEstado;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "Debe indicar si permite modificación")
    private Boolean permiteModificacion;

    @NotNull(message = "La prioridad es obligatoria")
    @Positive(message = "La prioridad debe ser mayor a 0")
    private Integer nivelPrioridad;

    @NotNull(message = "La fecha de creación es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    private LocalDate fechaCreacion;
}
