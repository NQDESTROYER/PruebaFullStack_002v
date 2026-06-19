package com.example.ms_sucursales.repository;

import com.example.ms_sucursales.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {

    // Native Query requerida por el PDF
    @Query(value = "SELECT * FROM sucursales WHERE operativa = true ORDER BY nombre ASC", nativeQuery = true)
    List<Sucursal> buscarOperativasOrdenadas();
}


