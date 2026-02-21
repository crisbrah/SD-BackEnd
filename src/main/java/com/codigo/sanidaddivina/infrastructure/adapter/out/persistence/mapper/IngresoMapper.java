package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper;

import com.codigo.sanidaddivina.domain.model.Ingreso;
import com.codigo.sanidaddivina.entities.IngresoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IngresoMapper {

    @Mapping(source = "idIngreso", target = "id")
    @Mapping(source = "persona.idPersona", target = "personaId")
    @Mapping(source = "miembro.idMiembro", target = "miembroId")
    Ingreso toDomain(IngresoEntity entity);

    @Mapping(source = "id", target = "idIngreso")
    @Mapping(target = "persona", ignore = true)
    @Mapping(target = "miembro", ignore = true)
    IngresoEntity toEntity(Ingreso domain);
}
