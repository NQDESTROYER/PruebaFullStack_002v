package com.example.ms_sucursales.config;

import com.example.ms_sucursales.entity.Region;
import com.example.ms_sucursales.entity.Sucursal;
import com.example.ms_sucursales.repository.RegionRepository;
import com.example.ms_sucursales.repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RegionRepository regionRepository;
    private final SucursalRepository sucursalRepository;

    @Override
    public void run(String... args) throws Exception {
        // Solo poblar si la tabla de regiones está vacía
        if (regionRepository.count() == 0) {

            // Creamos 1 región padre
            Region rm = new Region(null, "Región Metropolitana", "CL-RM", 52, false, LocalDate.now(), null);
            Region regionGuardada = regionRepository.save(rm);

            // Creamos las 3 sucursales requeridas
            Sucursal s1 = new Sucursal(null, "Sucursal Aeropuerto", "Av. Américo Vespucio 501", 120, true, LocalDateTime.now(), regionGuardada);
            Sucursal s2 = new Sucursal(null, "Sucursal Alameda", "Av. Libertador B. O'Higgins 2030", 45, true, LocalDateTime.now(), regionGuardada);
            Sucursal s3 = new Sucursal(null, "Sucursal Providencia", "Av. Providencia 1420", 30, false, LocalDateTime.now(), regionGuardada);

            sucursalRepository.save(s1);
            sucursalRepository.save(s2);
            sucursalRepository.save(s3);

            System.out.println("=== DATOS INICIALES CARGADOS CORRECTAMENTE ===");
        }
    }
}