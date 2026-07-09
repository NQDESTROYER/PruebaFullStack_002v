package com.example.ms_empleados.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 10)
    private String rut;

    @Column(nullable = false)
    private String nombreCompleto;

    @Column(nullable = false)
    private String cargo;

    @Column(nullable = false)
    private BigDecimal sueldoBase;

    @Column(nullable = false)
    private Boolean conContratoIndefinido;

    @Column(nullable = false)
    private LocalDate fechaContratacion;

    @Column(nullable = false)
    private Boolean activo;
}