package com.example.ms_pagos.assemblers;

import com.example.ms_pagos.controller.PagoController;
import com.example.ms_pagos.dto.PagoResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PagoModelAssembler implements RepresentationModelAssembler<PagoResponseDTO, EntityModel<PagoResponseDTO>> {

    @Override
    public EntityModel<PagoResponseDTO> toModel(PagoResponseDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(PagoController.class).buscarPorId(entity.getId())).withSelfRel(),
                linkTo(methodOn(PagoController.class).listarTodos()).withRel("pagos"));
    }
}
