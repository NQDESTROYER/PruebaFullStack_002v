package com.example.ms_reportes.client;

import com.example.ms_reportes.dto.SucursalDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(name = "ms-sucursales", path = "/api/v1/sucursales")
public interface SucursalClient {

    @GetMapping
    List<SucursalDTO> obtenerTodasLasSucursales();
}
