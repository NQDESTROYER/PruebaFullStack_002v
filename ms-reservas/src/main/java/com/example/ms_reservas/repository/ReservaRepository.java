package com.example.ms_reservas.repository;

import com.example.ms_reservas.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    // Query JPQL requerida por el PDF: Listar reservas desde una fecha, ordenadas de más reciente a más antigua
    @Query("SELECT r FROM Reserva r WHERE r.fechaInicio >= :fecha ORDER BY r.fechaInicio DESC")
    List<Reserva> buscarDesdeFecha(@Param("fecha") LocalDate fecha);
}
