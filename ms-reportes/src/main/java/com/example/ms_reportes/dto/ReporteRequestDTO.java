package com.example.ms_reportes.dto;

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
public class ReporteRequestDTO {

    @NotBlank(message = "El nombre del reporte es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombreReporte;

    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;

    @NotNull(message = "El total de registros no puede ser nulo")
    @Min(value = 0, message = "El total de registros no puede ser negativo")
    private Integer totalRegistros;

    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    @NotNull(message = "La fecha de emisión es obligatoria")
    @PastOrPresent(message = "La fecha de emisión no puede ser una fecha futura")
    private LocalDate fechaEmision;
}
