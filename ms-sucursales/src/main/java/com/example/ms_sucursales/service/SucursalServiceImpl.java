package com.example.ms_sucursales.service;

import com.example.ms_sucursales.dto.SucursalRequestDTO;
import com.example.ms_sucursales.dto.SucursalResponseDTO;
import com.example.ms_sucursales.model.Region;
import com.example.ms_sucursales.model.Sucursal;
import com.example.ms_sucursales.exception.ResourceNotFoundException;
import com.example.ms_sucursales.mapper.SucursalMapper;
import com.example.ms_sucursales.repository.RegionRepository;
import com.example.ms_sucursales.repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SucursalServiceImpl implements SucursalService {

    private final SucursalRepository sucursalRepository;
    private final RegionRepository regionRepository;
    private final SucursalMapper sucursalMapper;

    @Override
    @Transactional(readOnly = true)
    public List<SucursalResponseDTO> listarTodas() {
        log.info("Buscando todas las sucursales");
        return sucursalRepository.findAll().stream()
                .map(sucursalMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SucursalResponseDTO> listarOperativas() {
        log.info("Ejecutando Native Query para sucursales operativas");
        return sucursalRepository.buscarOperativasOrdenadas().stream()
                .map(sucursalMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SucursalResponseDTO obtenerPorId(Integer id) {
        log.info("Buscando sucursal con ID: {}", id);
        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sucursal no encontrada con ID: " + id));
        return sucursalMapper.toDto(sucursal);
    }

    @Override
    @Transactional
    public SucursalResponseDTO crear(SucursalRequestDTO dto) {
        log.info("Creando nueva sucursal: {}", dto.getNombre());
        Region region = regionRepository.findById(dto.getRegionId())
                .orElseThrow(() -> new ResourceNotFoundException("Región no encontrada con ID: " + dto.getRegionId()));

        Sucursal sucursal = sucursalMapper.toEntity(dto);
        sucursal.setRegion(region);
        return sucursalMapper.toDto(sucursalRepository.save(sucursal));
    }

    @Override
    @Transactional
    public SucursalResponseDTO actualizar(Integer id, SucursalRequestDTO dto) {
        log.info("Actualizando sucursal ID: {}", id);
        Sucursal sucursalExistente = sucursalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sucursal no encontrada con ID: " + id));

        Region region = regionRepository.findById(dto.getRegionId())
                .orElseThrow(() -> new ResourceNotFoundException("Región no encontrada con ID: " + dto.getRegionId()));

        sucursalExistente.setNombre(dto.getNombre());
        sucursalExistente.setDireccion(dto.getDireccion());
        sucursalExistente.setCapacidadAutos(dto.getCapacidadAutos());
        sucursalExistente.setOperativa(dto.isOperativa());
        sucursalExistente.setFechaApertura(dto.getFechaApertura());
        sucursalExistente.setRegion(region);

        return sucursalMapper.toDto(sucursalRepository.save(sucursalExistente));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        log.info("Eliminando sucursal ID: {}", id);
        if(!sucursalRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar, sucursal inexistente con ID: " + id);
        }
        sucursalRepository.deleteById(id);
    }
}


