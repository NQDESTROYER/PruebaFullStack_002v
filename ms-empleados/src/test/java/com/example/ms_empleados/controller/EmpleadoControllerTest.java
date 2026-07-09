package com.example.ms_empleados.controller;

import com.example.ms_empleados.assemblers.EmpleadoModelAssembler;
import com.example.ms_empleados.dto.EmpleadoRequestDTO;
import com.example.ms_empleados.dto.EmpleadoResponseDTO;
import com.example.ms_empleados.service.IEmpleadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmpleadoController.class)
class EmpleadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Mockeamos la Interfaz (no la implementación)
    @MockBean
    private IEmpleadoService service;

    // Mockeamos el Assembler de HATEOAS
    @MockBean
    private EmpleadoModelAssembler assembler;

    @Test
    @DisplayName("GET /api/v1/empleados - Debe retornar HTTP 200")
    void listarTodosTest() throws Exception {
        EmpleadoResponseDTO dto = EmpleadoResponseDTO.builder().id(1).nombreCompleto("Pedro").build();
        EntityModel<EmpleadoResponseDTO> model = EntityModel.of(dto);

        when(service.listarTodos()).thenReturn(List.of(dto));
        when(assembler.toModel(any(EmpleadoResponseDTO.class))).thenReturn(model);

        mockMvc.perform(get("/api/v1/empleados")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.empleadoResponseDTOList[0].nombreCompleto").value("Pedro"));
    }

    @Test
    @DisplayName("POST /api/v1/empleados - Debe retornar HTTP 201")
    void crearEmpleadoTest() throws Exception {
        EmpleadoRequestDTO request = EmpleadoRequestDTO.builder()
                .rut("12345678-9").nombreCompleto("Nuevo")
                .cargo("Dev").sueldoBase(new BigDecimal("1000"))
                .conContratoIndefinido(true).fechaContratacion(LocalDate.now())
                .activo(true).build();

        EmpleadoResponseDTO response = EmpleadoResponseDTO.builder()
                .id(1).rut("12345678-9").nombreCompleto("Nuevo").build();

        EntityModel<EmpleadoResponseDTO> model = EntityModel.of(response);

        when(service.crear(any(EmpleadoRequestDTO.class))).thenReturn(response);
        when(assembler.toModel(any(EmpleadoResponseDTO.class))).thenReturn(model);

        mockMvc.perform(post("/api/v1/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombreCompleto").value("Nuevo"));
    }
}