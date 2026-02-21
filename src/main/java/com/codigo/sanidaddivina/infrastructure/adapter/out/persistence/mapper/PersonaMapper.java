package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper;

import com.codigo.sanidaddivina.domain.model.Persona;
import com.codigo.sanidaddivina.entities.PersonaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", config = IglesiaMapperConfig.class)
public interface PersonaMapper {

    @Mapping(source = "idPersona", target = "id")
    @Mapping(source = "apePat", target = "apellidoPaterno")
    @Mapping(source = "apeMat", target = "apellidoMaterno")
    @Mapping(source = "esCivil", target = "estadoCivil")
    @Mapping(source = "numHijos", target = "numeroHijos")
    @Mapping(source = "estado", target = "activo")
    @Mapping(source = "iglesiaProcedencia.idIglesia", target = "iglesiaProcedenciaId")
    @Mapping(target = "miembroId",
        expression = "java(entity.getMiembro() != null ? entity.getMiembro().getIdMiembro() : null)")
    @Mapping(target = "estadoMembresia",
        expression = "java(entity.getMiembro() == null ? \"NO_MIEMBRO\" : (entity.getMiembro().isAprobado() ? \"APROBADO\" : (entity.getMiembro().isEstadoMiembro() ? \"PENDIENTE\" : \"RECHAZADO\")))")
    Persona toDomain(PersonaEntity entity);

    @Mapping(source = "id", target = "idPersona")
    @Mapping(source = "apellidoPaterno", target = "apePat")
    @Mapping(source = "apellidoMaterno", target = "apeMat")
    @Mapping(source = "estadoCivil", target = "esCivil")
    @Mapping(source = "numeroHijos", target = "numHijos")
    @Mapping(source = "activo", target = "estado")
    @Mapping(target = "iglesiaProcedencia", ignore = true)
    @Mapping(target = "miembro", ignore = true)
    PersonaEntity toEntity(Persona domain);

    @Mapping(source = "apellidoPaterno", target = "apePat")
    @Mapping(source = "apellidoMaterno", target = "apeMat")
    @Mapping(source = "estadoCivil", target = "esCivil")
    @Mapping(source = "numeroHijos", target = "numHijos")
    @Mapping(source = "activo", target = "estado")
    @Mapping(target = "iglesiaProcedencia", ignore = true)
    @Mapping(target = "miembro", ignore = true)
    void updateEntity(Persona domain, @MappingTarget PersonaEntity entity);
}
