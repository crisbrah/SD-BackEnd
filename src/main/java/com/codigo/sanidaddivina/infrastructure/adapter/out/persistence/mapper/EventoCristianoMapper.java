package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper;

import com.codigo.sanidaddivina.domain.model.EventoCristiano;
import com.codigo.sanidaddivina.entities.EventoCristianoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventoCristianoMapper {

    @Mapping(source = "idEvento", target = "id")
    @Mapping(target = "estadoEvento", source = "estadoEvento")
    EventoCristiano toDomain(EventoCristianoEntity entity);

    @Mapping(source = "id", target = "idEvento")
    @Mapping(target = "personas", ignore = true)
    EventoCristianoEntity toEntity(EventoCristiano domain);
}
