package com.example.ms_clientes.assemblers;

import com.example.ms_clientes.controller.ClienteController;
import com.example.ms_clientes.dto.ClienteResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ClienteModelAssembler implements RepresentationModelAssembler<ClienteResponseDTO, EntityModel<ClienteResponseDTO>> {

    @Override
    public EntityModel<ClienteResponseDTO> toModel(ClienteResponseDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(ClienteController.class).buscarPorId(entity.getId())).withSelfRel(),
                linkTo(methodOn(ClienteController.class).listarTodos()).withRel("clientes"));
    }
}
