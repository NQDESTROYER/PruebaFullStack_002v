package com.example.ms_clientes.repository;

import com.example.ms_clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    // Rúbrica: Buscar todos los clientes cuyo email contenga un texto dado, sin distinguir mayúsculas ni minúsculas.
    List<Cliente> findByEmailContainingIgnoreCase(String email);
}
