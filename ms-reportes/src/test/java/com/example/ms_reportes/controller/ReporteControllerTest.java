package com.example.ms_reportes.controller;

import com.example.ms_reportes.assemblers.ReporteModelAssembler;
import com.example.ms_reportes.dto.ReporteConsolidadoDTO;
import com.example.ms_reportes.dto.ReporteRequestDTO;
import com.example.ms_reportes.dto.ReporteResponseDTO;
import com.example.ms_reportes.service.ReporteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReporteController.class)
@Import(ReporteModelAssembler.class) // Importante para validar los links de HATEOAS
public class ReporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReporteService reporteService;

    @Autowired
    private ObjectMapper objectMapper;

    private ReporteResponseDTO responseDTO;
    private ReporteRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        responseDTO = ReporteResponseDTO.builder()
                .id(1)
                .nombreReporte("Reporte Test")
                .categoria("TESTING")
                .totalRegistros(10)
                .description("Desc Test")
                .fechaEmision(LocalDate.now())
                .procesado(true)
                .build();

        requestDTO = ReporteRequestDTO.builder()
                .nombreReporte("Reporte Test")
                .categoria("TESTING")
                .totalRegistros(10)
                .description("Desc Test")
                .fechaEmision(LocalDate.now())
                .build();
    }

    @Test
    void listarTodos_RetornaListaConHateoas() throws Exception {
        // Given
        when(reporteService.obtenerTodos()).thenReturn(Arrays.asList(responseDTO));

        // When & Then
        mockMvc.perform(get("/api/v1/reportes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.reporteResponseDTOList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.reporteResponseDTOList[0].nombreReporte").value("Reporte Test"))
                .andExpect(jsonPath("$._embedded.reporteResponseDTOList[0]._links.self.href").exists())
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void buscarPorId_RetornaReporteConHateoas() throws Exception {
        // Given
        when(reporteService.obtenerPorId(1)).thenReturn(responseDTO);

        // When & Then
        mockMvc.perform(get("/api/v1/reportes/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombreReporte").value("Reporte Test"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.reportes.href").exists());
    }

    @Test
    void crearReporte_RetornaCreated() throws Exception {
        // Given
        when(reporteService.crear(any(ReporteRequestDTO.class))).thenReturn(responseDTO);

        // When & Then
        mockMvc.perform(post("/api/v1/reportes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombreReporte").value("Reporte Test"));
    }

    @Test
    void obtenerConsolidadoCompleto_RetornaOk() throws Exception {
        // Given
        ReporteConsolidadoDTO consolidado = new ReporteConsolidadoDTO(
                "Éxito", 5, 5, new ArrayList<>(), new ArrayList<>()
        );
        when(reporteService.generarReporteConsolidadoCompleto()).thenReturn(consolidado);

        // When & Then
        mockMvc.perform(get("/api/v1/reportes/consolidado")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Éxito"))
                .andExpect(jsonPath("$.totalPagos").value(5))
                .andExpect(jsonPath("$.totalReservas").value(5));
    }

    @Test
    void eliminarReporte_RetornaNoContent() throws Exception {
        // Given
        doNothing().when(reporteService).eliminar(1);

        // When & Then
        mockMvc.perform(delete("/api/v1/reportes/{id}", 1))
                .andExpect(status().isNoContent());
    }
}