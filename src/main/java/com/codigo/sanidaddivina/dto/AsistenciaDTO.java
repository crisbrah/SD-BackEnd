package com.codigo.sanidaddivina.dto;


import com.codigo.sanidaddivina.entities.AsistenciaEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AsistenciaDTO {
    private Long idAsistencia;
    private LocalDateTime fechaAsistencia;
    private String tipoRegistro;
    //Relacion con la tabla persona
    private String idPersona;
    //Relacion con la tabla miembro
    private String idMiembro;

    public static AsistenciaDTO fromEntity(AsistenciaEntity asistenciaEntity){
        return AsistenciaDTO.builder()
                .idAsistencia(asistenciaEntity.getIdAsistencia())
                .fechaAsistencia(asistenciaEntity.getFechaAsistencia())
                .tipoRegistro(asistenciaEntity.getTipoRegistro() != null ? asistenciaEntity.getTipoRegistro().name() : null)
                //relacion con tablas
             //   .idPersona(asistenciaEntity.getPersona()
            //    .idMiembro(asistenciaEntity.getMiembro()
                .build();
    }
}
