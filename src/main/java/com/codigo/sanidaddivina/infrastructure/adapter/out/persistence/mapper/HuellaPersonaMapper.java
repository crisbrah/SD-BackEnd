package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper;

import com.codigo.sanidaddivina.domain.model.HuellaPersona;
import com.codigo.sanidaddivina.entities.HuellaPersonaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HuellaPersonaMapper {

    @Mapping(source = "idHuella", target = "id")
    @Mapping(source = "persona.idPersona", target = "personaId")
    HuellaPersona toDomain(HuellaPersonaEntity entity);

    @Mapping(source = "id", target = "idHuella")
    @Mapping(target = "persona", ignore = true)
    HuellaPersonaEntity toEntity(HuellaPersona domain);
}
