package com.example.ms_clientes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(min = 8, max = 12)
    @Column(nullable = false, unique = true, length = 12)
    private String rut;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(nullable = false, length = 100)
    private String nombreCompleto;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @DecimalMin("0.0")
    @Positive
    @Column(name = "ingreso_mensual", nullable = false)
    private BigDecimal ingresoMensual;

    @Builder.Default
    @Column(nullable = false)
    private boolean activo = true;

    @NotNull
    @Past
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Builder.Default
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Direccion> direcciones = new ArrayList<>();

    public void addDireccion(Direccion direccion) {
        direcciones.add(direccion);
        direccion.setCliente(this);
    }
}
