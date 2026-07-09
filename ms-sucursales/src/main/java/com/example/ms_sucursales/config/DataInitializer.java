package com.example.ms_sucursales.config;

import com.example.ms_sucursales.model.Region;
import com.example.ms_sucursales.model.Sucursal;
import com.example.ms_sucursales.repository.RegionRepository;
import com.example.ms_sucursales.repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final SucursalRepository sucursalRepository;
    private final RegionRepository regionRepository;

    @Bean
    CommandLineRunner initData() {
        return args -> {
            if (sucursalRepository.count() == 0) {
                log.info("Poblando 5 registros iniciales obligatorios en ms-sucursales...");

                Region r1 = Region.builder().nombreRegion("Metropolitana").codigoIso("RM").numeroComunas(52).zonaExtrema(false).fechaCreacion(LocalDate.now()).build();
                Region r2 = Region.builder().nombreRegion("Valparaiso").codigoIso("VALPO").numeroComunas(38).zonaExtrema(false).fechaCreacion(LocalDate.now()).build();
                regionRepository.saveAll(List.of(r1, r2));

                Sucursal s1 = Sucursal.builder().nombre("Sucursal Centro").direccion("Alameda 123").capacidadAutos(50).operativa(true).fechaApertura(LocalDateTime.now()).region(r1).build();
                Sucursal s2 = Sucursal.builder().nombre("Sucursal Norte").direccion("Ruta 5 Norte").capacidadAutos(100).operativa(true).fechaApertura(LocalDateTime.now()).region(r2).build();
                Sucursal s3 = Sucursal.builder().nombre("Sucursal Sur").direccion("Gran Avenida 456").capacidadAutos(20).operativa(false).fechaApertura(LocalDateTime.now()).region(r1).build();
                Sucursal s4 = Sucursal.builder().nombre("Sucursal Costa").direccion("San Martin 789").capacidadAutos(80).operativa(true).fechaApertura(LocalDateTime.now()).region(r2).build();
                Sucursal s5 = Sucursal.builder().nombre("Sucursal Oriente").direccion("Apoquindo 321").capacidadAutos(150).operativa(true).fechaApertura(LocalDateTime.now()).region(r1).build();

                sucursalRepository.saveAll(List.of(s1, s2, s3, s4, s5));
                log.info("Datos poblados exitosamente.");
            }
        };
    }
}