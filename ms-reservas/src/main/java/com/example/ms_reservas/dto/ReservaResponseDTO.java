package com.example.ms_reservas.dto;

import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
public class ReservaResponseDTO extends RepresentationModel<ReservaResponseDTO> {
    private Integer id;
    private String codigoReserva;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private BigDecimal montoTotal;
    private Boolean seguroIncluido;
    private Integer estadoId;
    private String nombreEstado;
    private Integer clienteId;
    private Integer vehiculoId;
}