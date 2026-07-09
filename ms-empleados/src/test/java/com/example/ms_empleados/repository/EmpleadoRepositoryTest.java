package com.example.ms_empleados.repository;

import com.example.ms_empleados.model.Empleado;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmpleadoRepositoryTest {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Test
    @DisplayName("Debe guardar un empleado correctamente en la BD")
    void guardarEmpleadoTest() {
        Empleado empleado = Empleado.builder()
                .rut("12345678-9").nombreCompleto("Carlos Prueba").cargo("Tester")
                .sueldoBase(new BigDecimal("1000000")).conContratoIndefinido(true)
                .fechaContratacion(LocalDate.now()).activo(true).build();

        Empleado guardado = empleadoRepository.save(empleado);

        assertNotNull(guardado.getId());
        assertEquals("Carlos Prueba", guardado.getNombreCompleto());
    }

    @Test
    @DisplayName("Debe encontrar empleados activos por año de contratación (Native Query)")
    void findEmpleadosActivosByYearTest() {
        Empleado emp1 = Empleado.builder()
                .rut("11111111-1").nombreCompleto("Activo 2023").cargo("Dev")
                .sueldoBase(new BigDecimal("1000")).conContratoIndefinido(true)
                .fechaContratacion(LocalDate.of(2023, 5, 10)).activo(true).build();

        Empleado emp2 = Empleado.builder()
                .rut("22222222-2").nombreCompleto("Inactivo 2023").cargo("Dev")
                .sueldoBase(new BigDecimal("1000")).conContratoIndefinido(true)
                .fechaContratacion(LocalDate.of(2023, 8, 20)).activo(false).build(); // Inactivo

        empleadoRepository.save(emp1);
        empleadoRepository.save(emp2);

        List<Empleado> resultado = empleadoRepository.findEmpleadosActivosByYear(2023);

        assertEquals(1, resultado.size());
        assertEquals("Activo 2023", resultado.get(0).getNombreCompleto());
    }
}