package com.example.ms_vehiculos.repository;

import com.example.ms_vehiculos.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {
    // Obtener vehículos disponibles y precio diario menor a valor indicado
    List<Vehiculo> findByDisponibleTrueAndPrecioDiarioLessThan(Double precioMaximo);
}
