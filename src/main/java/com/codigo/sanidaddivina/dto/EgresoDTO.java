package com.codigo.sanidaddivina.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
@Builder
public class EgresoDTO {
    private Long idEgreso;
    private String concepto;
    private float montoSalida;
    private Timestamp fechaIngreso;
    private String metodoSalida;
    private String usuaCrea;
    private Timestamp dateCreate;
    private String usuaModif;
    private Timestamp dateModif;
    private String usuaDelet;
    private Timestamp dateDelet;
    //Relacion con Persona
    private String idPersona;
    //Relacion con Miembro
    private String idMiembro;


    public static EgresoDTO fromEntity(EgresoDTO egresoDTO)
    {
        return EgresoDTO.builder()
                .idEgreso(egresoDTO.getIdEgreso())
                .concepto(egresoDTO.getConcepto())
                .montoSalida(egresoDTO.getMontoSalida())
                .fechaIngreso(egresoDTO.getFechaIngreso())
                .metodoSalida(egresoDTO.getMetodoSalida())
                .usuaCrea(egresoDTO.getUsuaCrea())
                .dateCreate(egresoDTO.getDateCreate())
                .usuaModif(egresoDTO.getUsuaModif())
                .dateModif(egresoDTO.getDateModif())
                .usuaDelet(egresoDTO.getUsuaDelet())
                .dateDelet(egresoDTO.getDateDelet())
                //relacion con tabla
                .idPersona(egresoDTO.getIdPersona())
                .idMiembro(egresoDTO.getIdMiembro())
                .build();
    }
}
