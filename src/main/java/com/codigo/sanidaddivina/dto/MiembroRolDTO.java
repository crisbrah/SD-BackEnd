package com.codigo.sanidaddivina.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Builder
public class MiembroRolDTO {

    private Long idMiembroRol;
    private Date inicioRol;
    private Date finRol;
    //  Relacion con miembro
    private String idMiembro;
    //relacion con Rol
    private String idRol;

    public static MiembroRolDTO fromEntity(MiembroRolDTO miembroRolDTO) {
        return MiembroRolDTO.builder()
                .idMiembroRol(miembroRolDTO.getIdMiembroRol())
                .inicioRol(miembroRolDTO.getInicioRol())
                .finRol(miembroRolDTO.getFinRol())
                //relacion
                .idMiembro(miembroRolDTO.getIdMiembro())
                .idRol(miembroRolDTO.getIdRol())
                .build();
    }
}
