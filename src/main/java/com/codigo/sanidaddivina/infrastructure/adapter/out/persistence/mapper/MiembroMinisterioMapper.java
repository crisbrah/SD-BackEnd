package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper;

import com.codigo.sanidaddivina.domain.model.MiembroMinisterio;
import com.codigo.sanidaddivina.entities.MiembroMinisterioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MiembroMinisterioMapper {

    @Mapping(source = "idMiembroMinisterio", target = "id")
    @Mapping(source = "miembro.idMiembro", target = "miembroId")
    @Mapping(source = "ministerio.idMinisterio", target = "ministerioId")
    MiembroMinisterio toDomain(MiembroMinisterioEntity entity);

    @Mapping(source = "id", target = "idMiembroMinisterio")
    @Mapping(target = "miembro", ignore = true)
    @Mapping(target = "ministerio", ignore = true)
    MiembroMinisterioEntity toEntity(MiembroMinisterio domain);
}
