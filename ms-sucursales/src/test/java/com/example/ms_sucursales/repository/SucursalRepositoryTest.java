package com.example.ms_sucursales.repository;

import com.example.ms_sucursales.model.Region;
import com.example.ms_sucursales.model.Sucursal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class SucursalRepositoryTest {

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Test
    void buscarOperativasOrdenadasTest() {
        Region r1 = Region.builder().nombreRegion("Sur").build();
        regionRepository.save(r1);

        Sucursal s1 = Sucursal.builder().nombre("Z").operativa(true).region(r1).build();
        Sucursal s2 = Sucursal.builder().nombre("A").operativa(true).region(r1).build();
        Sucursal s3 = Sucursal.builder().nombre("M").operativa(false).region(r1).build(); // Inactiva

        sucursalRepository.saveAll(List.of(s1, s2, s3));

        List<Sucursal> resultado = sucursalRepository.buscarOperativasOrdenadas();

        assertEquals(2, resultado.size());
        assertEquals("A", resultado.get(0).getNombre()); // Prueba el orden ASC
        assertTrue(resultado.get(0).isOperativa());
    }
}