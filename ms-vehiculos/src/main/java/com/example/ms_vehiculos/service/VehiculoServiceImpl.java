package com.example.ms_vehiculos.service;

import com.example.ms_vehiculos.dto.VehiculoRequestDTO;
import com.example.ms_vehiculos.dto.VehiculosResponseDTO;
import com.example.ms_vehiculos.model.Categoria;
import com.example.ms_vehiculos.model.Vehiculo;
import com.example.ms_vehiculos.exception.ResourceNotFoundException;
import com.example.ms_vehiculos.mapper.VehiculoMapper;
import com.example.ms_vehiculos.repository.CategoriaRepository;
import com.example.ms_vehiculos.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // inyecta dependencias automaticas
public class VehiculoServiceImpl implements IVehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final CategoriaRepository categoriaRepository; // <-- Agregamos el repo de categorías
    private final VehiculoMapper vehiculoMapper;

    @Override
    @Transactional
    public VehiculosResponseDTO crear(VehiculoRequestDTO dto){
        // 1. Buscamos que la categoría exista antes de hacer nada
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con el ID: " + dto.getCategoriaId()));

        // 2. Mapeamos y le asignamos la categoría encontrada
        Vehiculo vehiculo = vehiculoMapper.toEntity(dto);
        vehiculo.setCategoria(categoria);

        // 3. Guardamos
        Vehiculo vehiculoGuardado = vehiculoRepository.save(vehiculo);
        return vehiculoMapper.toResponseDTO(vehiculoGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculosResponseDTO> listarTodos() {
        return vehiculoRepository.findAll().stream()
                .map(vehiculoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public VehiculosResponseDTO buscarPorId(Integer id) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado con el ID: " + id));
        return vehiculoMapper.toResponseDTO(vehiculo);
    }

    @Override
    @Transactional
    public VehiculosResponseDTO actualizar(Integer id, VehiculoRequestDTO dto) {
        Vehiculo vehiculoExistente = vehiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar. Vehículo no encontrado con el ID: " + id));

        // Validamos que la nueva categoría que envían también exista
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con el ID: " + dto.getCategoriaId()));

        vehiculoExistente.setPatente(dto.getPatente());
        vehiculoExistente.setMarca(dto.getMarca());
        vehiculoExistente.setPrecioDiario(dto.getPrecioDiario());
        vehiculoExistente.setDisponible(dto.getDisponible());
        vehiculoExistente.setFechaIngreso(dto.getFechaIngreso());

        // Asignamos la nueva categoría
        vehiculoExistente.setCategoria(categoria);

        Vehiculo vehiculoActualizado = vehiculoRepository.save(vehiculoExistente);
        return vehiculoMapper.toResponseDTO(vehiculoActualizado);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        if (!vehiculoRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar. Vehículo no encontrado con el ID: " + id);
        }
        vehiculoRepository.deleteById(id);
    }

    // ===================================================================
    // IMPLEMENTACIÓN DE LOS MÉTODOS OBLIGATORIOS PARA SUB-RECURSOS
    // ===================================================================

    @Override
    @Transactional(readOnly = true)
    public Categoria obtenerCategoriaPorVehiculoId(Integer id) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado con el ID: " + id));

        return vehiculo.getCategoria();
    }

    @Override
    @Transactional
    public Categoria actualizarCategoria(Integer id, Integer nuevaCategoriaId) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado con el ID: " + id));

        Categoria nuevaCategoria = categoriaRepository.findById(nuevaCategoriaId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con el ID: " + nuevaCategoriaId));

        vehiculo.setCategoria(nuevaCategoria);
        vehiculoRepository.save(vehiculo);

        return nuevaCategoria;
    }
}