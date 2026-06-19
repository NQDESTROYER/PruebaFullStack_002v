package com.example.ms_reservas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "reservas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(min = 5, max = 50)
    @Column(name = "codigo_reserva", nullable = false, unique = true, length = 50)
    private String codigoReserva;

    @NotNull
    @FutureOrPresent
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @NotNull
    @Future
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @DecimalMin("0.0")
    @Positive
    @Column(name = "monto_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoTotal;

    @NotNull
    @Column(name = "seguro_incluido", nullable = false)
    private Boolean seguroIncluido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_id", nullable = false)
    private EstadoReserva estado;

    @Positive
    @Column(name = "cliente_id", nullable = false)
    private Integer clienteId;

    @Positive
    @Column(name = "vehiculo_id", nullable = false)
    private Integer vehiculoId;
}