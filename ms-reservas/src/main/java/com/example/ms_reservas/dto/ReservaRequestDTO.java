package com.example.ms_reservas.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ReservaRequestDTO {
    @NotBlank(message = "El código de reserva es obligatorio")
    private String codigoReserva;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @FutureOrPresent(message = "La fecha de inicio no puede estar en el pasado")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;

    @NotNull(message = "El monto total es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a cero")
    private BigDecimal montoTotal;

    @NotNull(message = "Debe indicar si incluye seguro")
    private Boolean seguroIncluido;

    @NotNull(message = "El ID del estado es obligatorio")
    private Integer estadoId;

    @NotNull(message = "El ID del cliente es obligatorio")
    private Integer clienteId;

    @NotNull(message = "El ID del vehículo es obligatorio")
    private Integer vehiculoId;
}