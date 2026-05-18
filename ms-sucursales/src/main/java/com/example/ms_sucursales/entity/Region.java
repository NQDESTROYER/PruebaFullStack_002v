package com.example.ms_sucursales.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "regiones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 5 atributos propios requeridos por la rúbrica
    private String nombreRegion;
    private String codigoIso;
    private Integer numeroComunas;
    private boolean zonaExtrema;
    private LocalDate fechaCreacion;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    private List<Sucursal> sucursales;
}
