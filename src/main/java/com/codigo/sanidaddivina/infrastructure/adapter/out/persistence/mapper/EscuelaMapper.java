package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper;

import com.codigo.sanidaddivina.domain.model.Escuela;
import com.codigo.sanidaddivina.entities.EscuelaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EscuelaMapper {

    @Mapping(source = "idEscuela", target = "id")
    Escuela toDomain(EscuelaEntity entity);

    @Mapping(source = "id", target = "idEscuela")
    EscuelaEntity toEntity(Escuela domain);
}
