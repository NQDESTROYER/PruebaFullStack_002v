package com.example.ms_pagos.repository;

import com.example.ms_pagos.model.Pago;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {

    //Uso de BigDecimal y soporte para Paginación (Page y Pageable)
    @Query("SELECT p FROM Pago p WHERE p.monto BETWEEN :min AND :max")
    Page<Pago> findPagosByMontoRange(@Param("min") BigDecimal min, @Param("max") BigDecimal max, Pageable pageable);
}