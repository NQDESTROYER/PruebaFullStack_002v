package com.example.ms_empleados.assemblers;

import com.example.ms_empleados.controller.EmpleadoController;
import com.example.ms_empleados.dto.EmpleadoResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EmpleadoModelAssembler implements RepresentationModelAssembler<EmpleadoResponseDTO, EntityModel<EmpleadoResponseDTO>> {

    @Override
    public EntityModel<EmpleadoResponseDTO> toModel(EmpleadoResponseDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(EmpleadoController.class).buscarPorId(entity.getId())).withSelfRel(),
                linkTo(methodOn(EmpleadoController.class).listarTodos()).withRel("empleados"));
    }
}
