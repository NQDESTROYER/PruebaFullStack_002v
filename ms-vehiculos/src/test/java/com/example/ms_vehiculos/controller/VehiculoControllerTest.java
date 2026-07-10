package com.example.ms_vehiculos.controller;

import com.example.ms_vehiculos.dto.VehiculoRequestDTO;
import com.example.ms_vehiculos.dto.VehiculosResponseDTO;
import com.example.ms_vehiculos.service.IVehiculoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VehiculoController.class)
class VehiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IVehiculoService vehiculoService;

    private VehiculoRequestDTO requestDTO;
    private VehiculosResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = VehiculoRequestDTO.builder()
                .patente("XX9999")
                .marca("Kia")
                .precioDiario(25000.0)
                .disponible(true)
                .fechaIngreso(LocalDate.now())
                .categoriaId(1)
                .build();

        responseDTO = VehiculosResponseDTO.builder()
                .id(1)
                .patente("XX9999")
                .marca("Kia")
                .precioDiario(25000.0)
                .disponible(true)
                .fechaIngreso(LocalDate.now())
                .build();
    }

    @Test
    void listarTodos_DebeRetornar200() throws Exception {
        when(vehiculoService.listarTodos()).thenReturn(Arrays.asList(responseDTO));

        mockMvc.perform(get("/api/vehiculos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].patente").value("XX9999"));
    }

    @Test
    void buscarPorId_DebeRetornar200() throws Exception {
        when(vehiculoService.buscarPorId(1)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/vehiculos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void crearVehiculo_DebeRetornar201() throws Exception {
        when(vehiculoService.crear(any(VehiculoRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/vehiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.patente").value("XX9999"));
    }

    @Test
    void eliminarVehiculo_DebeRetornar204() throws Exception {
        mockMvc.perform(delete("/api/vehiculos/1"))
                .andExpect(status().isNoContent());
    }
}