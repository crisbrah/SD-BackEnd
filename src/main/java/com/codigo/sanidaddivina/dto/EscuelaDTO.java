package com.codigo.sanidaddivina.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class EscuelaDTO {

    private Long idEscuela;
    private String nombreEscuela;
    private Timestamp fechaCreacion;
    private boolean estadoEscuela;
    private String usuaCrea;
    private Timestamp dateCreate;
    private String usuaModif;
    private Timestamp dateModif;
    private String usuaDelet;
    private Timestamp dateDelet;
    //relacion con celula
    private Long idCelula;

    public static EscuelaDTO fromEntity(EscuelaDTO escuelaDTO){
        return EscuelaDTO.builder()
                .idEscuela(escuelaDTO.getIdEscuela())
                .nombreEscuela(escuelaDTO.getNombreEscuela())
                .fechaCreacion(escuelaDTO.getFechaCreacion())
                .estadoEscuela(escuelaDTO.isEstadoEscuela())
                .usuaCrea(escuelaDTO.getUsuaCrea())
                .dateCreate(escuelaDTO.getDateCreate())
                .usuaModif(escuelaDTO.getUsuaModif())
                .dateModif(escuelaDTO.getDateModif())
                .usuaDelet(escuelaDTO.getUsuaDelet())
                .dateDelet(escuelaDTO.getDateDelet())
                //relacion con tablas
                .idCelula(escuelaDTO.getIdCelula())
                .build();
    }
}
