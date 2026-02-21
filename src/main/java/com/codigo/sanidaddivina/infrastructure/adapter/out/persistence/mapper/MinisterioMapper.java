package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper;

import com.codigo.sanidaddivina.domain.model.Ministerio;
import com.codigo.sanidaddivina.entities.MinisterioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MinisterioMapper {

    @Mapping(source = "idMinisterio", target = "id")
    Ministerio toDomain(MinisterioEntity entity);

    @Mapping(source = "id", target = "idMinisterio")
    MinisterioEntity toEntity(Ministerio domain);
}
