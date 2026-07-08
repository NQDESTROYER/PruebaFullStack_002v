package com.example.ms_reservas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Cliente Feign para el microservicio ms-vehiculos
@FeignClient(name = "ms-vehiculos", path = "/api/vehiculos")
public interface VehiculoClient {

    @GetMapping("/{id}")
    Object obtenerVehiculoPorId(@PathVariable("id") Integer id);
}