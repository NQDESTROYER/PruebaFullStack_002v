package com.example.ms_clientes.service;

import com.example.ms_clientes.dto.ClienteResponseDTO;
import com.example.ms_clientes.dto.ClientesRequestDTO;
import com.example.ms_clientes.dto.DireccionRequestDTO;
import com.example.ms_clientes.dto.DireccionResponseDTO;
import com.example.ms_clientes.model.Cliente;
import com.example.ms_clientes.model.Direccion;
import com.example.ms_clientes.exception.ResourceNotFoundException;
import com.example.ms_clientes.mapper.ClienteMapper;
import com.example.ms_clientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements IClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Override
    @Transactional
    public ClienteResponseDTO crear(ClientesRequestDTO dto) {
        Cliente cliente = clienteMapper.toEntity(dto);
        Cliente guardado = clienteRepository.save(cliente);
        return clienteMapper.toResponseDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorId(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con el ID: " + id));
        return clienteMapper.toResponseDTO(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ClienteResponseDTO actualizar(Integer id, ClientesRequestDTO dto) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar. Cliente no encontrado con el ID: " + id));

        clienteMapper.actualizarEntidad(dto, clienteExistente);
        Cliente actualizado = clienteRepository.save(clienteExistente);
        return clienteMapper.toResponseDTO(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede eliminar. Cliente no encontrado con el ID: " + id));
        clienteRepository.delete(cliente);
    }

    // ===================================================================
    // IMPLEMENTACIÓN OBLIGATORIA DE SUB-RECURSOS (CONTRATO CON IClienteService)
    // ===================================================================

    @Override
    @Transactional(readOnly = true)
    public DireccionResponseDTO obtenerDireccionPorClienteId(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con el ID: " + id));

        // Buscar en la lista la dirección marcada como principal
        Direccion direccionPrincipal = cliente.getDirecciones().stream()
                .filter(Direccion::isPrincipal)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("El cliente con ID " + id + " no tiene una dirección principal registrada"));

        return DireccionResponseDTO.builder()
                .calle(direccionPrincipal.getCalle())
                .numero(direccionPrincipal.getNumero())
                .ciudad(direccionPrincipal.getCiudad())
                .principal(direccionPrincipal.isPrincipal())
                .fechaRegistro(direccionPrincipal.getFechaRegistro())
                .build();
    }

    @Override
    @Transactional
    public DireccionResponseDTO actualizarDireccion(Integer id, DireccionRequestDTO direccionRequestDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con el ID: " + id));

        // Intentar encontrar una dirección principal existente
        Direccion direccion = cliente.getDirecciones().stream()
                .filter(Direccion::isPrincipal)
                .findFirst()
                .orElse(null);

        // Si no la posee, creamos la entidad y usamos el método utilitario addDireccion
        if (direccion == null) {
            direccion = new Direccion();
            direccion.setPrincipal(true);
            cliente.addDireccion(direccion);
        }

        // Poblamos la entidad Direccion con los datos de tu RequestDTO real
        direccion.setCalle(direccionRequestDTO.getCalle());
        direccion.setNumero(direccionRequestDTO.getNumero());
        direccion.setCiudad(direccionRequestDTO.getCiudad());
        direccion.setFechaRegistro(direccionRequestDTO.getFechaRegistro());

        // Guardamos el cliente (y la cascada actualizará o guardará la dirección en Laragon)
        clienteRepository.save(cliente);

        return DireccionResponseDTO.builder()
                .calle(direccion.getCalle())
                .numero(direccion.getNumero())
                .ciudad(direccion.getCiudad())
                .principal(direccion.isPrincipal())
                .fechaRegistro(direccion.getFechaRegistro())
                .build();
    }
}