package com.example.ms_reservas.assemblers;

import com.example.ms_reservas.controller.ReservaController;
import com.example.ms_reservas.dto.ReservaResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ReservaModelAssembler implements RepresentationModelAssembler<ReservaResponseDTO, EntityModel<ReservaResponseDTO>> {

    @Override
    public EntityModel<ReservaResponseDTO> toModel(ReservaResponseDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(ReservaController.class).obtenerPorId(entity.getId())).withSelfRel(),
                linkTo(methodOn(ReservaController.class).obtenerTodas()).withRel("reservas"));
    }
}
