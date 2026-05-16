package com.example.ms_vehiculos.repository;

import com.example.ms_vehiculos.entity.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository//indica que esto manejara os componentes de acceso a la base de datos
public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {
    // a lo que yo entendi, nos ayudamos de JpaRepository ya que esto es como una biblioteca que trae todas las funciones que necesitamos
    //el poner vehiculo es pq le explica a laragon con que tabla debe trabajar y el integer le indica cual es la clave primaria

}
