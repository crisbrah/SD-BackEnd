package com.codigo.sanidaddivina.dto;

import com.codigo.sanidaddivina.entities.CelulaEntity;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CelulaDTO {
    private Long idCelula;
    private String nombreCelula;
    private boolean estadoCelula;
    private String usuaCrea;
    private Timestamp dateCreate;
    private String usuaModif;
    private Timestamp dateModif;
    private String usuaDelet;
    private Timestamp dateDelet;

    public static CelulaDTO fromEntity(CelulaEntity entity) {
        System.out.println("Converting entity to DTO: " + entity.getIdCelula() + " - " + entity.getNombreCelula());

        CelulaDTO dto = CelulaDTO.builder()
                .idCelula(entity.getIdCelula())
                .nombreCelula(entity.getNombreCelula())
                .estadoCelula(entity.isEstadoCelula())
                .usuaCrea(entity.getUsuaCrea())
                .dateCreate(entity.getDateCreate())
                .usuaModif(entity.getUsuaModif())
                .dateModif(entity.getDateModif())
                .usuaDelet(entity.getUsuaDelet())
                .dateDelet(entity.getDateDelet())
                .build();

        System.out.println("Converted DTO: " + dto.getIdCelula() + " - " + dto.getNombreCelula());
        return dto;
    }
}
