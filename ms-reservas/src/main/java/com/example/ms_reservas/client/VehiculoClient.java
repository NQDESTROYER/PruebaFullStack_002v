package com.example.ms_reservas.client;

import com.example.ms_reservas.dto.VehiculoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-vehiculos", path = "/api/vehiculos")
public interface VehiculoClient {

    @GetMapping("/{id}")
    VehiculoDTO obtenerVehiculoPorId(@PathVariable("id") Integer id);
}