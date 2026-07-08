package com.example.ms_vehiculos.service;

import com.example.ms_vehiculos.dto.VehiculoRequestDTO;
import com.example.ms_vehiculos.dto.VehiculosResponseDTO;
import com.example.ms_vehiculos.model.Vehiculo;
import com.example.ms_vehiculos.exception.ResourceNotFoundException;
import com.example.ms_vehiculos.mapper.VehiculoMapper;
import com.example.ms_vehiculos.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor//inyecta dependencias automaticas
public class VehiculoServiceImpl implements IVehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final VehiculoMapper vehiculoMapper;

    @Override
    public VehiculosResponseDTO crear(VehiculoRequestDTO dto){
        Vehiculo vehiculo = vehiculoMapper.toEntity(dto);
        Vehiculo vehiculoGuardado = vehiculoRepository.save(vehiculo);
        return vehiculoMapper.toResponseDTO(vehiculoGuardado);
    }

    @Override
    public List<VehiculosResponseDTO> listarTodos() {
        // Buscamos todos los registros en Laragon y los transformamos uno a uno a ResponseDTO
        return vehiculoRepository.findAll().stream()
                .map(vehiculoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VehiculosResponseDTO buscarPorId(Integer id) {
        // Si no lo encuentra, dispara el Guardia (ResourceNotFoundException) que creamos al inicio
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado con el ID: " + id));
        return vehiculoMapper.toResponseDTO(vehiculo);
    }

    @Override
    public VehiculosResponseDTO actualizar(Integer id, VehiculoRequestDTO dto) {
        // Primero verificamos que exista en la base de datos
        Vehiculo vehiculoExistente = vehiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar. Vehículo no encontrado con el ID: " + id));

        // Modificamos los datos antiguos con los nuevos que vienen del DTO
        vehiculoExistente.setPatente(dto.getPatente());
        vehiculoExistente.setMarca(dto.getMarca());
        vehiculoExistente.setPrecioDiario(dto.getPrecioDiario());
        vehiculoExistente.setDisponible(dto.getDisponible());
        vehiculoExistente.setFechaIngreso(dto.getFechaIngreso());

        Vehiculo vehiculoActualizado = vehiculoRepository.save(vehiculoExistente);
        return vehiculoMapper.toResponseDTO(vehiculoActualizado);
    }

    @Override
    public void eliminar(Integer id) {
        // Verificamos si existe antes de borrar
        if (!vehiculoRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar. Vehículo no encontrado con el ID: " + id);
        }
        vehiculoRepository.deleteById(id);
    }

}

