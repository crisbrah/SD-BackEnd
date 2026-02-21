package com.codigo.sanidaddivina.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class ProfesorDTO {

    private Long idProfesor;
    private String nombreProfesor;
    private String correoProfesor;
    private boolean estadoProfesor;
    private String usuaCrea;
    private Timestamp dateCreate;
    private String usuaModif;
    private Timestamp dateModif;
    private String usuaDelet;
    private Timestamp dateDelet;

    public static ProfesorDTO fromEntity(ProfesorDTO profesorDTO){
        return ProfesorDTO.builder()
                .idProfesor(profesorDTO.getIdProfesor())
                .nombreProfesor(profesorDTO.getNombreProfesor())
                .correoProfesor(profesorDTO.getCorreoProfesor())
                .estadoProfesor(profesorDTO.isEstadoProfesor())
                .usuaCrea(profesorDTO.getUsuaCrea())
                .dateCreate(profesorDTO.getDateCreate())
                .usuaModif(profesorDTO.getUsuaModif())
                .dateModif(profesorDTO.getDateModif())
                .usuaDelet(profesorDTO.getUsuaDelet())
                .dateDelet(profesorDTO.getDateDelet())
                .build();
    }
}