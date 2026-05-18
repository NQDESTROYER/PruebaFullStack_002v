package com.example.ms_reportes.dto;

import lombok.Data;

@Data
public class ReservaDTO {
    private Integer id;
    private Integer clienteId;
    private Integer vehiculoId;
    private String estado;
}
