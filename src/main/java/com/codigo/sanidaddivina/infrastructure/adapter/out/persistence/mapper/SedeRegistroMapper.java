package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper;

import com.codigo.sanidaddivina.domain.model.SedeRegistro;
import com.codigo.sanidaddivina.entities.SedeRegistroEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SedeRegistroMapper {

    @Mapping(source = "idSedeRegistro", target = "id")
    @Mapping(source = "sede.idSede", target = "sedeId")
    @Mapping(source = "persona.idPersona", target = "personaId")
    SedeRegistro toDomain(SedeRegistroEntity entity);

    @Mapping(source = "id", target = "idSedeRegistro")
    @Mapping(target = "sede", ignore = true)
    @Mapping(target = "persona", ignore = true)
    SedeRegistroEntity toEntity(SedeRegistro domain);
}
