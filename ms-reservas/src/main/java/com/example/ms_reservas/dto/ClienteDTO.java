package com.example.ms_reservas.dto;

import lombok.Data;

@Data
public class ClienteDTO {
    private Integer id;
    private String rut;
    private String nombre;
    private String correo;
}