package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper;

import com.codigo.sanidaddivina.domain.model.CursoInscripcion;
import com.codigo.sanidaddivina.entities.CursoPersonaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CursoInscripcionMapper {

    @Mapping(source = "idCursoPersona", target = "id")
    @Mapping(source = "curso.idCurso", target = "cursoId")
    @Mapping(source = "persona.idPersona", target = "personaId")
    @Mapping(target = "metodoPago", ignore = true)
    CursoInscripcion toDomain(CursoPersonaEntity entity);

    @Mapping(source = "id", target = "idCursoPersona")
    @Mapping(target = "curso", ignore = true)
    @Mapping(target = "persona", ignore = true)
    CursoPersonaEntity toEntity(CursoInscripcion domain);
}
