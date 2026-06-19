package com.example.ms_empleados.repository;

import com.example.ms_empleados.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    // Rúbrica: Listar empleados activos contratados en un año determinado
    @Query(value = "SELECT * FROM empleados WHERE activo = true AND YEAR(fecha_contratacion) = :year", nativeQuery = true)
    List<Empleado> findEmpleadosActivosByYear(@Param("year") Integer year);
}
