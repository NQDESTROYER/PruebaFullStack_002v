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

                // --- 1. Creamos 5 Categorías ---
                Categoria c1 = Categoria.builder().nombre("Sedan").descripcion("Auto familiar").prioridad(1).activa(true).fechaRegistro(LocalDateTime.now()).build();
                Categoria c2 = Categoria.builder().nombre("SUV").descripcion("Auto para terrenos").prioridad(2).activa(true).fechaRegistro(LocalDateTime.now()).build();
                Categoria c3 = Categoria.builder().nombre("Camioneta").descripcion("Ideal para carga y trabajo").prioridad(3).activa(true).fechaRegistro(LocalDateTime.now()).build();
                Categoria c4 = Categoria.builder().nombre("Deportivo").descripcion("Alta velocidad y rendimiento").prioridad(4).activa(true).fechaRegistro(LocalDateTime.now()).build();
                Categoria c5 = Categoria.builder().nombre("Hatchback").descripcion("Compacto y económico para ciudad").prioridad(5).activa(true).fechaRegistro(LocalDateTime.now()).build();

                categoriaRepository.saveAll(List.of(c1, c2, c3, c4, c5));

                // --- 2. Creamos 5 Vehículos ---
                Vehiculo v1 = Vehiculo.builder().patente("AA-11-BB").marca("Toyota").precioDiario(50.0).disponible(true).fechaIngreso(LocalDate.now()).categoria(c1).build();
                Vehiculo v2 = Vehiculo.builder().patente("CC-22-DD").marca("Ford").precioDiario(80.0).disponible(true).fechaIngreso(LocalDate.now()).categoria(c2).build();
                Vehiculo v3 = Vehiculo.builder().patente("EE-33-FF").marca("Honda").precioDiario(40.0).disponible(true).fechaIngreso(LocalDate.now()).categoria(c1).build();
                Vehiculo v4 = Vehiculo.builder().patente("GG-44-HH").marca("Chevrolet").precioDiario(90.0).disponible(false).fechaIngreso(LocalDate.now()).categoria(c3).build();
                Vehiculo v5 = Vehiculo.builder().patente("II-55-JJ").marca("Nissan").precioDiario(35.0).disponible(true).fechaIngreso(LocalDate.now()).categoria(c5).build();

                vehiculoRepository.saveAll(List.of(v1, v2, v3, v4, v5));

                log.info("Datos poblados exitosamente con 5 registros cada uno.");
            } else {
                log.info("La base de datos ya contiene información, omitiendo inicialización.");
            }
        };
    }
}