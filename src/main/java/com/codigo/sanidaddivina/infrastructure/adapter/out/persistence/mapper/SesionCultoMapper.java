package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper;

import com.codigo.sanidaddivina.domain.model.SesionCulto;
import com.codigo.sanidaddivina.entities.SesionCultoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SesionCultoMapper {

    @Mapping(source = "idSesion", target = "id")
    @Mapping(source = "sede.idSede", target = "sedeId")
    SesionCulto toDomain(SesionCultoEntity entity);

    @Mapping(source = "id", target = "idSesion")
    @Mapping(target = "sede", ignore = true)
    SesionCultoEntity toEntity(SesionCulto domain);
}
