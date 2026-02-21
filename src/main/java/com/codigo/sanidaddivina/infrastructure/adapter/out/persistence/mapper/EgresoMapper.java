package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper;

import com.codigo.sanidaddivina.domain.model.Egreso;
import com.codigo.sanidaddivina.entities.EgresoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EgresoMapper {

    @Mapping(source = "persona.idPersona", target = "personaId")
    @Mapping(source = "miembro.idMiembro", target = "miembroId")
    Egreso toDomain(EgresoEntity entity);

    @Mapping(target = "persona", ignore = true)
    @Mapping(target = "miembro", ignore = true)
    EgresoEntity toEntity(Egreso domain);
}
