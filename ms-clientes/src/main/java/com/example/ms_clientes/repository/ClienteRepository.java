package com.example.ms_clientes.repository;

import com.example.ms_clientes.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    // Al heredar de JpaRepository, Spring nos regala automáticamente
    // todos los métodos del CRUD: save(), findById(), findAll(), deleteById(), etc
}
