package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper;

import com.codigo.sanidaddivina.domain.model.Miembro;
import com.codigo.sanidaddivina.entities.MiembroEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MiembroMapper {

    @Mapping(source = "idMiembro", target = "id")
    @Mapping(source = "persona.idPersona", target = "personaId")
    @Mapping(source = "celula.idCelula", target = "celulaId")
    @Mapping(target = "roles", ignore = true)
    Miembro toDomain(MiembroEntity entity);

    @Mapping(source = "id", target = "idMiembro")
    @Mapping(target = "persona", ignore = true)
    @Mapping(target = "celula", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    MiembroEntity toEntity(Miembro domain);
}
