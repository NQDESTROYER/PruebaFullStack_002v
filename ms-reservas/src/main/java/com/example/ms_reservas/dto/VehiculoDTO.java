package com.example.ms_reservas.dto;

import lombok.Data;

@Data
public class VehiculoDTO {
    private Integer id;
    private String patente;
    private String marca;
    private String estado; // Ej: "DISPONIBLE", "EN_MANTENCION"
}