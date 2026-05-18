package com.example.ms_reservas.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EstadoReservaResponseDTO {
    private Integer id;
    private String nombreEstado;
    private String descripcion;
    private Boolean permiteModificacion;
    private Integer nivelPrioridad;
    private LocalDate fechaCreacion;
}
