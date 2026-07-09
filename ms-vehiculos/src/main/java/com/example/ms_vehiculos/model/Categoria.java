package com.example.ms_vehiculos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "categorias")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank
    @Size(min = 5, max = 255)
    @Column(nullable = false)
    private String descripcion;

    @Positive
    @Column(nullable = false)
    private Integer prioridad;

    @Builder.Default
    @Column(nullable = false)
    private boolean activa = true;

    @NotNull
    @PastOrPresent
    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    // --- EL ESCUDO CONTRA EL STACKOVERFLOW ---
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<Vehiculo> vehiculos;
}