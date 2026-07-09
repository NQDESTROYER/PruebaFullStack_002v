package com.example.ms_reservas.model;

import jakarta.persistence.*;
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

    // Limpiamos las anotaciones de validación. Queda solo la definición de BD.
    @Column(name = "codigo_reserva", nullable = false, unique = true, length = 50)
    private String codigoReserva;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "monto_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoTotal;

    @Column(name = "seguro_incluido", nullable = false)
    private Boolean seguroIncluido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_id", nullable = false)
    private EstadoReserva estado;

    @Column(name = "cliente_id", nullable = false)
    private Integer clienteId;

    @Column(name = "vehiculo_id", nullable = false)
    private Integer vehiculoId;
}