package com.example.ms_reservas.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "estados_reserva")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoReserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_estado", nullable = false, unique = true, length = 50)
    private String nombreEstado;

    @Column(nullable = false, length = 150)
    private String descripcion;

    @Column(name = "permite_modificacion", nullable = false)
    private Boolean permiteModificacion;

    @Column(name = "nivel_prioridad", nullable = false)
    private Integer nivelPrioridad;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;

    // Retiramos el cascade = CascadeType.ALL para proteger las reservas.
    // Solo mapeamos la relación bidireccional.
    @OneToMany(mappedBy = "estado")
    private List<Reserva> reservas;
}