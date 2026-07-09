package com.example.ms_reportes.repository;

import com.example.ms_reportes.entity.Reporte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ReporteRepositoryTest {

    @Autowired
    private ReporteRepository reporteRepository;

    private Reporte reporte;

    @BeforeEach
    void setUp() {
        reporte = new Reporte();
        reporte.setNombreReporte("Reporte de Prueba");
        reporte.setCategoria("TESTING");
        reporte.setTotalRegistros(100);
        reporte.setFechaEmision(LocalDate.now());
        reporte.setProcesado(true);
        reporte.setDescription("Descripción de test");
    }

    @Test
    void guardarYBuscarPorId_Exito() {
        // When
        Reporte guardado = reporteRepository.save(reporte);
        Optional<Reporte> encontrado = reporteRepository.findById(guardado.getId());

        // Then
        assertTrue(encontrado.isPresent());
        assertEquals("Reporte de Prueba", encontrado.get().getNombreReporte());
        assertEquals("TESTING", encontrado.get().getCategoria());
        assertEquals(100, encontrado.get().getTotalRegistros());
    }

    @Test
    void listarTodos_RetornaLista() {
        // Given
        Reporte reporte2 = new Reporte(null, "Otro Reporte", "TESTING", 50, LocalDate.now(), false, "Otra desc");
        reporteRepository.save(reporte);
        reporteRepository.save(reporte2);

        // When
        List<Reporte> lista = reporteRepository.findAll();

        // Then
        assertEquals(2, lista.size());
    }

    @Test
    void eliminarReporte_Exito() {
        // Given
        Reporte guardado = reporteRepository.save(reporte);

        // When
        reporteRepository.deleteById(guardado.getId());
        Optional<Reporte> buscado = reporteRepository.findById(guardado.getId());

        // Then
        assertFalse(buscado.isPresent());
    }
}