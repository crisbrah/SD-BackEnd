package com.codigo.sanidaddivina.dto;
import com.codigo.sanidaddivina.entities.MinisterioEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MinisterioDTO {

    private Long idMinisterio;
    private String nombreMinisterio;
    private boolean estadoMinisterio;
    private String usuaCrea;
    private Timestamp dateCreate;
    private String usuaModif;
    private Timestamp dateModif;
    private String usuaDelet;
    private Timestamp dateDelet;

    public static MinisterioDTO fromEntity(MinisterioEntity ministerioDTO){
        return MinisterioDTO.builder()
                .idMinisterio(ministerioDTO.getIdMinisterio())
                .nombreMinisterio(ministerioDTO.getNombreMinisterio())
                .estadoMinisterio(ministerioDTO.isEstadoMinisterio())
                .usuaCrea(ministerioDTO.getUsuaCrea())
                .dateCreate(ministerioDTO.getDateCreate())
                .usuaModif(ministerioDTO.getUsuaModif())
                .dateModif(ministerioDTO.getDateModif())
                .usuaDelet(ministerioDTO.getUsuaDelet())
                .dateDelet(ministerioDTO.getDateDelet())
                .build();
    }
}
