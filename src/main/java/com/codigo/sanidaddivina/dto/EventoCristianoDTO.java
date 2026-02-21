package com.codigo.sanidaddivina.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EventoCristianoDTO {
    private Long idEvento;
    private String nombreEvento;
    private boolean estadoEvento;
    //Relacio con PersonaEvento
    private Long idPersonaEvento;

    public static EventoCristianoDTO fromEntity(EventoCristianoDTO eventoCristianoDTO){
        return EventoCristianoDTO.builder()
                .idEvento(eventoCristianoDTO.getIdEvento())
                .nombreEvento(eventoCristianoDTO.getNombreEvento())
                .estadoEvento(eventoCristianoDTO.isEstadoEvento())
                //Relacion
                .idPersonaEvento(eventoCristianoDTO.getIdPersonaEvento())
                .build();
    }
}

