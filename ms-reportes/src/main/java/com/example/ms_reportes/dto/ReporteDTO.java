package com.example.ms_reportes.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ReporteDTO {
    private Integer id;
    private String nombreReporte;
    private String categoria;
    private Integer totalRegistros;
    private String description;
    private LocalDate fechaEmision;
    private Boolean procesado;
}