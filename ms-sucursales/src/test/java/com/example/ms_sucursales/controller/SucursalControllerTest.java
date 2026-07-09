package com.example.ms_sucursales.controller;

import com.example.ms_sucursales.assemblers.SucursalModelAssembler;
import com.example.ms_sucursales.dto.SucursalRequestDTO;
import com.example.ms_sucursales.dto.SucursalResponseDTO;
import com.example.ms_sucursales.service.SucursalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SucursalController.class)
class SucursalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SucursalService sucursalService;

    // Se debe mockear el assembler porque el Controller lo necesita para HATEOAS
    @MockBean
    private SucursalModelAssembler assembler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void obtenerPorIdTest_Exito() throws Exception {
        // 1. Preparar datos simulados
        SucursalResponseDTO dto = new SucursalResponseDTO();
        dto.setId(1);
        dto.setNombre("Sucursal Centro");

        // 2. Comportamiento esperado de los mocks
        when(sucursalService.obtenerPorId(1)).thenReturn(dto);
        when(assembler.toModel(any())).thenReturn(EntityModel.of(dto));

        // 3. Ejecutar petición GET y verificar (Status 200)
        mockMvc.perform(get("/api/v1/sucursales/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Sucursal Centro"));
    }

    @Test
    void borrarTest_Exito() throws Exception {
        // 1. Comportamiento esperado (no hace nada al eliminar, asumiendo éxito)
        doNothing().when(sucursalService).eliminar(1);

        // 2. Ejecutar petición DELETE y verificar (Status 204 No Content)
        mockMvc.perform(delete("/api/v1/sucursales/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void registrarNuevaTest_Exito() throws Exception {
        // 1. Preparar Request
        SucursalRequestDTO request = new SucursalRequestDTO();
        request.setNombre("Nueva Sucursal");
        request.setDireccion("Calle 123");
        request.setCapacidadAutos(50);
        request.setOperativa(true);
        request.setFechaApertura(LocalDateTime.now().minusDays(1)); // Fecha pasada válida
        request.setRegionId(1);

        // 2. Preparar Response simulado
        SucursalResponseDTO response = new SucursalResponseDTO();
        response.setId(2);
        response.setNombre("Nueva Sucursal");

        // 3. Mocks
        when(sucursalService.crear(any(SucursalRequestDTO.class))).thenReturn(response);
        when(assembler.toModel(any())).thenReturn(EntityModel.of(response));

        // 4. Ejecutar POST y verificar (Status 201 Created)
        mockMvc.perform(post("/api/v1/sucursales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Nueva Sucursal"));
    }
}