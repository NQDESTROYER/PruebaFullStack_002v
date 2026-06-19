package com.example.ms_pagos.service;

import com.example.ms_pagos.model.Pago;
import java.util.List;

public interface PagoService {
    List<Pago> obtenerTodos();
    Pago obtenerPorId(Integer id);
    Pago registrarPago(Pago pago);
}