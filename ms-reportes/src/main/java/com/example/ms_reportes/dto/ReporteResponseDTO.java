package com.example.ms_reportes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteResponseDTO {
    private Integer id;
    private String nombreReporte;
    private String categoria;
    private Integer totalRegistros;
    private String description;
    private LocalDate fechaEmision;
    private Boolean procesado;
}
