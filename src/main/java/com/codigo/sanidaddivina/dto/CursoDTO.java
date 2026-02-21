package com.codigo.sanidaddivina.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
@Builder
public class CursoDTO {
    private Long idCurso;
    private String nombreCurso;
    private Timestamp fechaCreacion;
    private boolean estadoCurso;
    private String usuaCrea;
    private Timestamp dateCreate;
    private String usuaModif;
    private Timestamp dateModif;
    private String usuaDelet;
    private Timestamp dateDelet;
    // Relación con Escuela
    private String idEscuela;
    // Relación muchos a uno con ProfesorEntity
    private String idProfesor;

    public static CursoDTO fromEntity(CursoDTO cursoDTO){
        return CursoDTO.builder()
                .idCurso(cursoDTO.getIdCurso())
                .nombreCurso(cursoDTO.getNombreCurso())
                .fechaCreacion(cursoDTO.getFechaCreacion())
                .estadoCurso(cursoDTO.isEstadoCurso())
                .usuaCrea(cursoDTO.getUsuaCrea())
                .dateCreate(cursoDTO.getDateCreate())
                .usuaModif(cursoDTO.getUsuaModif())
                .dateModif(cursoDTO.getDateModif())
                .usuaDelet(cursoDTO.getUsuaDelet())
                .dateDelet(cursoDTO.getDateDelet())
                //relacion con tablas
                .idEscuela(cursoDTO.getIdEscuela())
                .idProfesor(cursoDTO.getIdProfesor())
                .build();
    }
}
