package com.example.ms_vehiculos.assemblers;

import com.example.ms_vehiculos.controller.VehiculoController;
import com.example.ms_vehiculos.dto.VehiculosResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class VehiculoModelAssembler implements RepresentationModelAssembler<VehiculosResponseDTO, EntityModel<VehiculosResponseDTO>> {

    @Override
    public EntityModel<VehiculosResponseDTO> toModel(VehiculosResponseDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(VehiculoController.class).buscarPorId(entity.getId())).withSelfRel(),
                linkTo(methodOn(VehiculoController.class).listarTodos()).withRel("vehiculos"));
    }
}
