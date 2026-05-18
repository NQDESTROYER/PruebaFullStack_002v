package com.example.ms_reportes.dto;

import lombok.Data;

@Data
public class SucursalDTO {
    private Integer id;
    private String nombre;
    private String direccion;
    private Boolean operativa;
}