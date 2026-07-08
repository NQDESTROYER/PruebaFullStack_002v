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
                log.info("Poblando datos iniciales en ms-sucursales...");
                
                Region r1 = Region.builder().nombreRegion("Metropolitana").build();
                Region r2 = Region.builder().nombreRegion("Valparaiso").build();
                regionRepository.saveAll(List.of(r1, r2));

                Sucursal s1 = Sucursal.builder().nombre("Sucursal Centro").operativa(true).region(r1).build();
                Sucursal s2 = Sucursal.builder().nombre("Sucursal Norte").operativa(true).region(r2).build();
                Sucursal s3 = Sucursal.builder().nombre("Sucursal Sur").operativa(false).region(r1).build();
                sucursalRepository.saveAll(List.of(s1, s2, s3));
                
                log.info("Datos poblados exitosamente.");
            }
        };
    }
}

