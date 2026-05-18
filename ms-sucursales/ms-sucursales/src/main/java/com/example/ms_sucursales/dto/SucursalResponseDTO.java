package com.example.ms_sucursales.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SucursalResponseDTO {
    private Integer id;
    private String nombre;
    private String direccion;
    private Integer capacidadAutos;
    private boolean operativa;
    private LocalDateTime fechaApertura;
    private Integer regionId;
}
