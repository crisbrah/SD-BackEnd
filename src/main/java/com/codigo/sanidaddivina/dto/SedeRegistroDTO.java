package com.codigo.sanidaddivina.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class SedeRegistroDTO {

    private Long idSedeRegistro;
    private boolean estadoRegistro;
    private Timestamp fechaRegistro;

    //relacion con sede
    private String idSede;
    //relacion con Persona
    private String idPersona;

    public static SedeRegistroDTO fromEntity(SedeRegistroDTO sedeRegistroDTO){
        return SedeRegistroDTO.builder()
                .idSedeRegistro(sedeRegistroDTO.getIdSedeRegistro())
                .estadoRegistro(sedeRegistroDTO.isEstadoRegistro())
                .fechaRegistro(sedeRegistroDTO.getFechaRegistro())
                //relacion
                .idPersona(sedeRegistroDTO.getIdPersona())
                .idSede(sedeRegistroDTO.getIdSede())
                .build();
    }
}
