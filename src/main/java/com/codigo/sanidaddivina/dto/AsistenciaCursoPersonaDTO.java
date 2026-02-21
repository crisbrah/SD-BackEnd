package com.codigo.sanidaddivina.dto;


import com.codigo.sanidaddivina.entities.AsistenciaCursoPersonaEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class AsistenciaCursoPersonaDTO {
    private Long idAsistenciaCurso;
    private Timestamp fechaAsistenciaCurso;
    //Relacion con la tabla Curso
    private Long idCurso;
    //Relacion con la tabla persona
    private Long idPersona;

    public static AsistenciaCursoPersonaDTO fromEntity(AsistenciaCursoPersonaEntity asistenciaCursoPersonaEntity){

        return AsistenciaCursoPersonaDTO.builder()
                .idAsistenciaCurso(asistenciaCursoPersonaEntity.getIdAsistenciaCurso())
                .fechaAsistenciaCurso(asistenciaCursoPersonaEntity.getFechaAsistenciaCurso())
                //las siguientes 2 son relaciones
             //  .idCurso(asistenciaCursoPersonaEntity.getIdCurso())
               // .idPersona(asistenciaCursoPersonaEntity.getIdPersona())
                .build();
    }
}
