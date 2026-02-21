package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper;

import com.codigo.sanidaddivina.domain.model.AsistenciaCursoPersona;
import com.codigo.sanidaddivina.entities.AsistenciaCursoPersonaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AsistenciaCursoPersonaMapper {

    @Mapping(source = "idAsistenciaCurso", target = "id")
    @Mapping(source = "curso.idCurso", target = "cursoId")
    @Mapping(source = "persona.idPersona", target = "personaId")
    AsistenciaCursoPersona toDomain(AsistenciaCursoPersonaEntity entity);

    @Mapping(source = "id", target = "idAsistenciaCurso")
    @Mapping(target = "curso", ignore = true)
    @Mapping(target = "persona", ignore = true)
    AsistenciaCursoPersonaEntity toEntity(AsistenciaCursoPersona domain);

}
