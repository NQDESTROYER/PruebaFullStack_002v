package com.example.ms_reservas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Apunta al microservicio (ms-vehiculos) en el puerto 8082
@FeignClient(name = "ms-vehiculos", url = "http://localhost:8082/api/v1/vehiculos")
public interface VehiculoClient {

    @GetMapping("/{id}")
    Object obtenerVehiculoPorId(@PathVariable("id") Integer id);
}