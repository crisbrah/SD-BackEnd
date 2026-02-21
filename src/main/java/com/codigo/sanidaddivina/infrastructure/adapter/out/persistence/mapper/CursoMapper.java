package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper;

import com.codigo.sanidaddivina.domain.model.Curso;
import com.codigo.sanidaddivina.entities.CursoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CursoMapper {

    @Mapping(source = "idCurso", target = "id")
    @Mapping(source = "escuela.idEscuela", target = "escuelaId")
    @Mapping(source = "profesor.idProfesor", target = "profesorId")
    Curso toDomain(CursoEntity entity);

    @Mapping(source = "id", target = "idCurso")
    @Mapping(target = "escuela", ignore = true)
    @Mapping(target = "profesor", ignore = true)
    CursoEntity toEntity(Curso domain);
}
