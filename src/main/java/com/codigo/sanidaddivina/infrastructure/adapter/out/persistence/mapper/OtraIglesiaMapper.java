package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper;

import com.codigo.sanidaddivina.domain.model.OtraIglesia;
import com.codigo.sanidaddivina.entities.OtraIglesiaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OtraIglesiaMapper {

    @Mapping(source = "idIglesia", target = "id")
    OtraIglesia toDomain(OtraIglesiaEntity entity);

    @Mapping(source = "id", target = "idIglesia")
    OtraIglesiaEntity toEntity(OtraIglesia domain);
}
