package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper;

import com.codigo.sanidaddivina.domain.model.Asistencia;
import com.codigo.sanidaddivina.entities.AsistenciaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AsistenciaMapper {

    @Mapping(source = "idAsistencia", target = "id")
    @Mapping(source = "persona.idPersona", target = "personaId")
    @Mapping(target = "personaNombres", expression = "java(entity.getPersona() != null ? entity.getPersona().getNombres() + ' ' + entity.getPersona().getApePat() + ' ' + entity.getPersona().getApeMat() : null)")
    @Mapping(target = "personaDni", expression = "java(entity.getPersona() != null ? entity.getPersona().getDni() : null)")
    @Mapping(source = "miembro.idMiembro", target = "miembroId")
    @Mapping(source = "sesionCulto.idSesion", target = "sesionId")
    Asistencia toDomain(AsistenciaEntity entity);

    @Mapping(source = "id", target = "idAsistencia")
    @Mapping(target = "persona", ignore = true)
    @Mapping(target = "miembro", ignore = true)
    @Mapping(target = "sesionCulto", ignore = true)
    @Mapping(target = "usuaCrea", ignore = true)
    @Mapping(target = "dateCreate", ignore = true)
    @Mapping(target = "usuaModif", ignore = true)
    @Mapping(target = "dateModif", ignore = true)
    @Mapping(target = "usuaDelet", ignore = true)
    @Mapping(target = "dateDelet", ignore = true)
    AsistenciaEntity toEntity(Asistencia domain);
}
