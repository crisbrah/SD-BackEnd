package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper;

import com.codigo.sanidaddivina.domain.model.Celula;
import com.codigo.sanidaddivina.entities.CelulaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CelulaMapper {

    @Mapping(source = "idCelula", target = "id")
    Celula toDomain(CelulaEntity entity);

    @Mapping(source = "id", target = "idCelula")
    CelulaEntity toEntity(Celula domain);
}
