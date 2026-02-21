package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper;

import com.codigo.sanidaddivina.domain.model.Rol;
import com.codigo.sanidaddivina.entities.RolEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RolMapper {

    @Mapping(source = "idRol", target = "id")
    Rol toDomain(RolEntity entity);

    @Mapping(source = "id", target = "idRol")
    RolEntity toEntity(Rol domain);
}
