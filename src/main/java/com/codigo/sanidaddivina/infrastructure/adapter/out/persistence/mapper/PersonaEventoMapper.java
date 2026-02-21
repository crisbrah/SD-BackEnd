package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper;

import com.codigo.sanidaddivina.domain.model.PersonaEvento;
import com.codigo.sanidaddivina.entities.PersonaEventoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PersonaEventoMapper {

    @Mapping(source = "idPersonaEvento", target = "id")
    @Mapping(source = "evento.idEvento", target = "eventoId")
    @Mapping(source = "persona.idPersona", target = "personaId")
    PersonaEvento toDomain(PersonaEventoEntity entity);

    @Mapping(source = "id", target = "idPersonaEvento")
    @Mapping(target = "evento", ignore = true)
    @Mapping(target = "persona", ignore = true)
    PersonaEventoEntity toEntity(PersonaEvento domain);
}
