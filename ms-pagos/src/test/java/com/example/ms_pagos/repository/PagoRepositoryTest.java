package com.example.ms_pagos.repository;

import com.example.ms_pagos.model.MetodoPago;
import com.example.ms_pagos.model.Pago;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class PagoRepositoryTest {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    private MetodoPago metodoPago;

    @BeforeEach
    void setUp() {
        // 1. Crear y guardar un método de pago primero (para la llave foránea)
        metodoPago = new MetodoPago(null, "TEST_METHOD", "Método de prueba", true);
        metodoPago = metodoPagoRepository.save(metodoPago);

        // 2. Crear y guardar varios pagos con diferentes montos
        Pago pago1 = new Pago(null, "TRX-TEST-01", 1, new BigDecimal("10000.00"), LocalDateTime.now(), "APROBADO", metodoPago);
        Pago pago2 = new Pago(null, "TRX-TEST-02", 2, new BigDecimal("50000.00"), LocalDateTime.now(), "PENDIENTE", metodoPago);
        Pago pago3 = new Pago(null, "TRX-TEST-03", 3, new BigDecimal("100000.00"), LocalDateTime.now(), "APROBADO", metodoPago);

        pagoRepository.save(pago1);
        pagoRepository.save(pago2);
        pagoRepository.save(pago3);
    }

    @Test
    void findPagosByMontoRange_RetornaPagosEnRango() {
        // Given
        BigDecimal min = new BigDecimal("20000.00");
        BigDecimal max = new BigDecimal("80000.00");
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("fechaPago").descending());

        // When
        Page<Pago> result = pagoRepository.findPagosByMontoRange(min, max, pageRequest);

        // Then
        assertEquals(1, result.getTotalElements());
        assertEquals(new BigDecimal("50000.00"), result.getContent().get(0).getMonto());
        assertEquals("TRX-TEST-02", result.getContent().get(0).getCodigoTransaccion());
    }

    @Test
    void findPagosByMontoRange_NingunPagoEnRango_RetornaVacio() {
        // Given
        BigDecimal min = new BigDecimal("200000.00");
        BigDecimal max = new BigDecimal("500000.00");
        PageRequest pageRequest = PageRequest.of(0, 10);

        // When
        Page<Pago> result = pagoRepository.findPagosByMontoRange(min, max, pageRequest);

        // Then
        assertTrue(result.isEmpty());
        assertEquals(0, result.getTotalElements());
    }
}