package com.example.ms_.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "reportes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Atributo 1: String [cite: 28]
    @Column(name = "nombre_reporte", nullable = false, length = 100)
    private String nombreReporte;

    // Atributo 2: String [cite: 28]
    @Column(nullable = false, length = 50)
    private String categoria;

    // Atributo 3: Numérico de negocio [cite: 30, 32]
    @Column(name = "total_registros", nullable = false)
    private Integer totalRegistros;

    // Atributo 4: Fecha [cite: 31]
    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;

    // Atributo 5: Boolean de estado [cite: 30]
    @Column(nullable = false)
    private Boolean procesado = true;

    // Atributo 6: String descriptivo coherente con el dominio [cite: 28, 38]
    @Column(nullable = false, length = 255)
    private String description;
}
