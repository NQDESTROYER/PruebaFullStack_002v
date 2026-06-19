package com.example.ms_pagos.client;

import com.example.ms_pagos.dto.ReservaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-reservas", path = "/api/v1/reservas")
public interface ReservaClient {

    @GetMapping("/{id}")
    ReservaDTO obtenerReservaPorId(@PathVariable("id") Integer id);
}
