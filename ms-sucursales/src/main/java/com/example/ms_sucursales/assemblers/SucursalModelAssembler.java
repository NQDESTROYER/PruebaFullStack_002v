package com.example.ms_sucursales.assemblers;

import com.example.ms_sucursales.controller.SucursalController;
import com.example.ms_sucursales.dto.SucursalResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class SucursalModelAssembler implements RepresentationModelAssembler<SucursalResponseDTO, EntityModel<SucursalResponseDTO>> {

    @Override
    public EntityModel<SucursalResponseDTO> toModel(SucursalResponseDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(SucursalController.class).obtenerPorId(entity.getId())).withSelfRel(),
                linkTo(methodOn(SucursalController.class).obtenerTodas()).withRel("sucursales"));
    }
}
