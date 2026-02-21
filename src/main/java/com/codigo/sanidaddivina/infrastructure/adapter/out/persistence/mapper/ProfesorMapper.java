package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper;

import com.codigo.sanidaddivina.domain.model.Profesor;
import com.codigo.sanidaddivina.entities.ProfesorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfesorMapper {

    @Mapping(source = "idProfesor", target = "id")
    @Mapping(source = "miembro.idMiembro", target = "miembroId")
    Profesor toDomain(ProfesorEntity entity);

    @Mapping(source = "id", target = "idProfesor")
    @Mapping(target = "miembro", ignore = true)
    ProfesorEntity toEntity(Profesor domain);
}
