package com.example.ms_clientes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// hace que sea auto incrementable el ID
    private Integer id;
    
    //---5 atributos
    @Column(nullable = false, unique = true, length = 10)
    private String rut;

    @Column (nullable = false)
    private String nombreCompleto;

    @Column(nullable = false)
    private BigDecimal ingresoMensual;

    @Column(nullable = false)
    private Boolean activo;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;
}
