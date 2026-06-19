package com.example.ms_reportes.client;

import com.example.ms_reportes.dto.ReservaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(name = "ms-reservas")
public interface ReservaClient {

    @GetMapping
    List<ReservaDTO> obtenerTodasLasReservas();
}
