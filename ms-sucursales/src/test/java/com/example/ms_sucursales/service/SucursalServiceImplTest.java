package com.example.ms_sucursales.service;

import com.example.ms_sucursales.dto.SucursalRequestDTO;
import com.example.ms_sucursales.dto.SucursalResponseDTO;
import com.example.ms_sucursales.exception.ResourceNotFoundException;
import com.example.ms_sucursales.mapper.SucursalMapper;
import com.example.ms_sucursales.model.Region;
import com.example.ms_sucursales.model.Sucursal;
import com.example.ms_sucursales.repository.RegionRepository;
import com.example.ms_sucursales.repository.SucursalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SucursalServiceImplTest {

    @Mock
    private SucursalRepository sucursalRepository;
    @Mock
    private RegionRepository regionRepository;
    @Mock
    private SucursalMapper sucursalMapper;

    @InjectMocks
    private SucursalServiceImpl sucursalService;

    @Test
    void crearSucursalExito() {
        SucursalRequestDTO req = new SucursalRequestDTO();
        req.setRegionId(1);
        req.setNombre("Nueva");

        Region regionMock = new Region();
        regionMock.setId(1);

        Sucursal entityMock = new Sucursal();
        SucursalResponseDTO resMock = new SucursalResponseDTO();
        resMock.setId(1);
        resMock.setNombre("Nueva");

        when(regionRepository.findById(1)).thenReturn(Optional.of(regionMock));
        when(sucursalMapper.toEntity(any())).thenReturn(entityMock);
        when(sucursalRepository.save(any())).thenReturn(entityMock);
        when(sucursalMapper.toDto(any())).thenReturn(resMock);

        SucursalResponseDTO resultado = sucursalService.crear(req);

        assertEquals("Nueva", resultado.getNombre());
        verify(sucursalRepository, times(1)).save(any());
    }

    @Test
    void obtenerPorId_NoExisteLanzaExcepcion() {
        when(sucursalRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            sucursalService.obtenerPorId(99);
        });
    }
}