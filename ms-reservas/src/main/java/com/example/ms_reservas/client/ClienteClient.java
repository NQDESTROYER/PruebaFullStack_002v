package com.example.ms_reservas.client;

import com.example.ms_reservas.dto.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-clientes", path = "/api/clientes")
public interface ClienteClient {

    @GetMapping("/{id}")
    ClienteDTO obtenerClientePorId(@PathVariable("id") Integer id);
}