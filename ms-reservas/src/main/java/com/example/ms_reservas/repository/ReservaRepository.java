package com.example.ms_reservas.repository;

import com.example.ms_reservas.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    // Query JPQL requerida por el PDF
    @Query("SELECT r FROM Reserva r WHERE r.fechaInicio >= :fecha ORDER BY r.fechaInicio DESC")
    List<Reserva> buscarDesdeFecha(@Param("fecha") LocalDate fecha);

    // Métodos mágicos de Spring Data para reglas de negocio
    Optional<Reserva> findByCodigoReserva(String codigoReserva);

    // Sirve para evitar arrendar el mismo vehículo dos veces en fechas conflictivas
    boolean existsByVehiculoIdAndEstado_NombreEstado(Integer vehiculoId, String nombreEstado);
}