package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper;

import com.codigo.sanidaddivina.domain.model.MiembroRol;
import com.codigo.sanidaddivina.entities.MiembroRolEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MiembroRolMapper {

    @Mapping(source = "idMiembroRol", target = "id")
    @Mapping(source = "miembro.idMiembro", target = "miembroId")
    @Mapping(source = "rol.idRol", target = "rolId")
    MiembroRol toDomain(MiembroRolEntity entity);

    @Mapping(source = "id", target = "idMiembroRol")
    @Mapping(target = "miembro", ignore = true)
    @Mapping(target = "rol", ignore = true)
    MiembroRolEntity toEntity(MiembroRol domain);
}
