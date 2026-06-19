package com.example.ms_clientes.service;

import com.example.ms_clientes.dto.ClienteResponseDTO;
import com.example.ms_clientes.dto.ClientesRequestDTO;
import com.example.ms_clientes.model.Cliente;
import com.example.ms_clientes.exception.ResourceNotFoundException;
import com.example.ms_clientes.mapper.ClienteMapper;
import com.example.ms_clientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
//Aquí es donde juntamostodo lo que hemos hecho el Repository para guardar, el Mapper para traducir y las Excepciones por si algo sale mal.
public class ClienteServiceImpl implements IClienteService{
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Override
    public ClienteResponseDTO crear(ClientesRequestDTO dto) {
        Cliente cliente = clienteMapper.toEntity(dto);
        Cliente guardado = clienteRepository.save(cliente);
        return clienteMapper.toResponseDTO(guardado);
    }

    @Override
    public ClienteResponseDTO buscarPorId(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con el ID: " + id));
        return clienteMapper.toResponseDTO(cliente);
    }

    @Override
    public List<ClienteResponseDTO> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteResponseDTO actualizar(Integer id, ClientesRequestDTO dto) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar. Cliente no encontrado con el ID: " + id));

        // Actualizamos los datos de la entidad con lo que viene del DTO
        clienteExistente.setRut(dto.getRut());
        clienteExistente.setNombreCompleto(dto.getNombreCompleto());
        clienteExistente.setIngresoMensual(dto.getIngresoMensual());
        clienteExistente.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        clienteExistente.setFechaNacimiento(dto.getFechaNacimiento());

        Cliente actualizado = clienteRepository.save(clienteExistente);
        return clienteMapper.toResponseDTO(actualizado);
    }

    @Override
    public void eliminar(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede eliminar. Cliente no encontrado con el ID: " + id));
        clienteRepository.delete(cliente);
    }
}
