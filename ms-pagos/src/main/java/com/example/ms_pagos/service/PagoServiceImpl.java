package com.example.ms_pagos.service;

import com.example.ms_pagos.client.ReservaClient;
import com.example.ms_pagos.dto.ReservaDTO;
import com.example.ms_pagos.entity.Pago;
import com.example.ms_pagos.repository.PagoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;
    private final ReservaClient reservaClient;

    public PagoServiceImpl(PagoRepository pagoRepository, ReservaClient reservaClient) {
        this.pagoRepository = pagoRepository;
        this.reservaClient = reservaClient;
    }

    @Override
    public List<Pago> obtenerTodos() {
        return pagoRepository.findAll();
    }

    @Override
    public Pago obtenerPorId(Integer id) {
        return pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + id));
    }

    @Override
    public Pago registrarPago(Pago pago) {
        try {
            ReservaDTO reserva = reservaClient.obtenerReservaPorId(pago.getReservaId());
            if (reserva == null) {
                throw new RuntimeException("La reserva no existe.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error comunicándose con ms-reservas.");
        }

        pago.setCodigoTransaccion("TRX-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        pago.setFechaPago(LocalDateTime.now());
        pago.setEstadoPago("APROBADO");

        return pagoRepository.save(pago);
    }
}
