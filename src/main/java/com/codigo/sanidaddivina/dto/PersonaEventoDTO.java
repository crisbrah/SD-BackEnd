package com.codigo.sanidaddivina.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Builder
public class PersonaEventoDTO {

    private Long idPersonaEvento;
    private Date fechaEvento;
    //relacion con evento
    private String idEvento;
    //relacion con persona
    private String idPersona;

    public static PersonaEventoDTO fromEntity(PersonaEventoDTO personaEventoDTO)
    {
        return PersonaEventoDTO.builder()
                .idPersonaEvento(personaEventoDTO.getIdPersonaEvento())
                .fechaEvento(personaEventoDTO.getFechaEvento())
                //relacion
                .idEvento(personaEventoDTO.getIdEvento())
                .idPersona(personaEventoDTO.getIdPersona())
                .build();
    }
}

