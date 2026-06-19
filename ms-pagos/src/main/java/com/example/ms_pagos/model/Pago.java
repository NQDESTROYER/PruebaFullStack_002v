package com.example.ms_pagos.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codigo_transaccion", nullable = false, unique = true, length = 100)
    private String codigoTransaccion;

    @Column(name = "reserva_id", nullable = false)
    private Integer reservaId;

    @Column(nullable = false)
    private BigDecimal monto;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;

    @Column(name = "estado_pago", nullable = false, length = 50)
    private String estadoPago;

    @ManyToOne
    @JoinColumn(name = "metodo_id", nullable = false)
    private MetodoPago metodoPago;
}
