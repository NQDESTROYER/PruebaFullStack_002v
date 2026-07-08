package com.example.ms_pagos.repository;

import com.example.ms_pagos.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    // Obtener pagos en un rango de monto, ordenados desde el más reciente
    @Query("SELECT p FROM Pago p WHERE p.monto BETWEEN :min AND :max ORDER BY p.fechaPago DESC")
    List<Pago> findPagosByMontoRange(@Param("min") Double min, @Param("max") Double max);
}