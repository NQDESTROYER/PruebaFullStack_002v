package com.example.ms_reportes.assemblers;

import com.example.ms_reportes.controller.ReporteController;
import com.example.ms_reportes.dto.ReporteResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ReporteModelAssembler implements RepresentationModelAssembler<ReporteResponseDTO, EntityModel<ReporteResponseDTO>> {

    @Override
    public EntityModel<ReporteResponseDTO> toModel(ReporteResponseDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(ReporteController.class).buscarPorId(entity.getId())).withSelfRel(),
                linkTo(methodOn(ReporteController.class).listarTodos()).withRel("reportes"));
    }
}
