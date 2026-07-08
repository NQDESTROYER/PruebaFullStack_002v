package com.example.ms_vehiculos.config;

import com.example.ms_vehiculos.model.Categoria;
import com.example.ms_vehiculos.model.Vehiculo;
import com.example.ms_vehiculos.repository.CategoriaRepository;
import com.example.ms_vehiculos.repository.VehiculoRepository;
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

    private final VehiculoRepository vehiculoRepository;
    private final CategoriaRepository categoriaRepository;

    @Bean
    CommandLineRunner initData() {
        return args -> {
            if (vehiculoRepository.count() == 0) {
                log.info("Poblando datos iniciales en ms-vehiculos...");

                Categoria c1 = Categoria.builder().nombre("Sedan").descripcion("Auto familiar").prioridad(1).activa(true).fechaRegistro(LocalDateTime.now()).build();
                Categoria c2 = Categoria.builder().nombre("SUV").descripcion("Auto para terrenos").prioridad(2).activa(true).fechaRegistro(LocalDateTime.now()).build();
                categoriaRepository.saveAll(List.of(c1, c2));

                Vehiculo v1 = Vehiculo.builder().patente("AA-11-BB").marca("Toyota").precioDiario(50.0).disponible(true).fechaIngreso(LocalDate.now()).categoria(c1).build();
                Vehiculo v2 = Vehiculo.builder().patente("CC-22-DD").marca("Ford").precioDiario(80.0).disponible(true).fechaIngreso(LocalDate.now()).categoria(c2).build();
                Vehiculo v3 = Vehiculo.builder().patente("EE-33-FF").marca("Honda").precioDiario(40.0).disponible(true).fechaIngreso(LocalDate.now()).categoria(c1).build();
                vehiculoRepository.saveAll(List.of(v1, v2, v3));

                log.info("Datos poblados exitosamente.");
            }
        };
    }
}
