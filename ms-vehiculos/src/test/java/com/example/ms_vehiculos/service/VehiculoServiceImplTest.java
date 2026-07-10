package com.example.ms_vehiculos.service;

import com.example.ms_vehiculos.dto.VehiculoRequestDTO;
import com.example.ms_vehiculos.dto.VehiculosResponseDTO;
import com.example.ms_vehiculos.exception.ResourceNotFoundException;
import com.example.ms_vehiculos.mapper.VehiculoMapper;
import com.example.ms_vehiculos.model.Categoria;
import com.example.ms_vehiculos.model.Vehiculo;
import com.example.ms_vehiculos.repository.CategoriaRepository;
import com.example.ms_vehiculos.repository.VehiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehiculoServiceImplTest {

    @Mock
    private VehiculoRepository vehiculoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private VehiculoMapper vehiculoMapper;

    @InjectMocks
    private VehiculoServiceImpl vehiculoService;

    private Vehiculo vehiculo;
    private Categoria categoria;
    private VehiculoRequestDTO requestDTO;
    private VehiculosResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(1);
        categoria.setNombre("Sedan");

        vehiculo = new Vehiculo();
        vehiculo.setId(1);
        vehiculo.setPatente("AB1234");
        vehiculo.setCategoria(categoria);

        requestDTO = new VehiculoRequestDTO();
        requestDTO.setCategoriaId(1);
        requestDTO.setPatente("AB1234");

        responseDTO = new VehiculosResponseDTO();
        responseDTO.setId(1);
        responseDTO.setPatente("AB1234");
    }

    @Test
    void crearVehiculo_Exitoso() {
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        when(vehiculoMapper.toEntity(any())).thenReturn(vehiculo);
        when(vehiculoRepository.save(any())).thenReturn(vehiculo);
        when(vehiculoMapper.toResponseDTO(any())).thenReturn(responseDTO);

        VehiculosResponseDTO resultado = vehiculoService.crear(requestDTO);

        assertNotNull(resultado);
        assertEquals("AB1234", resultado.getPatente());
        verify(vehiculoRepository, times(1)).save(any());
    }

    @Test
    void buscarPorId_Exitoso() {
        when(vehiculoRepository.findById(1)).thenReturn(Optional.of(vehiculo));
        when(vehiculoMapper.toResponseDTO(vehiculo)).thenReturn(responseDTO);

        VehiculosResponseDTO resultado = vehiculoService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    @Test
    void buscarPorId_NoEncontrado() {
        when(vehiculoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> vehiculoService.buscarPorId(99));
    }

    @Test
    void eliminar_Exitoso() {
        when(vehiculoRepository.existsById(1)).thenReturn(true);

        vehiculoService.eliminar(1);

        verify(vehiculoRepository, times(1)).deleteById(1);
    }
}