package com.codigo.sanidaddivina.dto;

import com.codigo.sanidaddivina.entities.RolEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RolDTO {
    private Long idRol;
    private String nombreRol;
    private boolean estadoRol;
    private String usuaCrea;
    private Timestamp dateCreate;
    private String usuaModif;
    private Timestamp dateModif;
    private String usuaDelet;
    private Timestamp dateDelet;

    public static RolDTO fromEntity(RolEntity rolDTO){
        return RolDTO.builder()
                .idRol(rolDTO.getIdRol())
                .nombreRol(rolDTO.getNombreRol())
                .estadoRol(rolDTO.isEstadoRol())
                .usuaCrea(rolDTO.getUsuaCrea())
                .dateCreate(rolDTO.getDateCreate())
                .usuaModif(rolDTO.getUsuaModif())
                .dateModif(rolDTO.getDateModif())
                .usuaDelet(rolDTO.getUsuaDelet())
                .dateDelet(rolDTO.getDateDelet())
                .build();
    }
}
