package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper;

import com.codigo.sanidaddivina.domain.model.Sede;
import com.codigo.sanidaddivina.entities.SedeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SedeMapper {

    @Mapping(source = "idSede", target = "id")
    @Mapping(target = "provincia", ignore = true)
    Sede toDomain(SedeEntity entity);

    @Mapping(source = "id", target = "idSede")
    SedeEntity toEntity(Sede domain);
}
