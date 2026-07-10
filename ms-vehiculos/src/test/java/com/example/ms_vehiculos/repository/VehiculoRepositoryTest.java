package com.example.ms_vehiculos.repository;

import com.example.ms_vehiculos.model.Categoria;
import com.example.ms_vehiculos.model.Vehiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class VehiculoRepositoryTest {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = Categoria.builder()
                .nombre("SUV")
                .descripcion("Vehículo utilitario deportivo")
                .prioridad(1)
                .activa(true)
                .fechaRegistro(LocalDateTime.now())
                .build();
        categoriaRepository.save(categoria);
    }

    @Test
    void testFindByDisponibleTrueAndPrecioDiarioLessThan() {
        // Arrange
        Vehiculo v1 = Vehiculo.builder().patente("AA1111").marca("Toyota").precioDiario(20000.0).disponible(true).fechaIngreso(LocalDate.now()).categoria(categoria).build();
        Vehiculo v2 = Vehiculo.builder().patente("BB2222").marca("Honda").precioDiario(50000.0).disponible(true).fechaIngreso(LocalDate.now()).categoria(categoria).build();
        Vehiculo v3 = Vehiculo.builder().patente("CC3333").marca("Mazda").precioDiario(15000.0).disponible(false).fechaIngreso(LocalDate.now()).categoria(categoria).build();

        vehiculoRepository.save(v1);
        vehiculoRepository.save(v2);
        vehiculoRepository.save(v3);

        // Act
        List<Vehiculo> resultado = vehiculoRepository.findByDisponibleTrueAndPrecioDiarioLessThan(30000.0);

        // Assert
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getPatente()).isEqualTo("AA1111");
    }
}