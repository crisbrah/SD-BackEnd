package com.codigo.sanidaddivina.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OtraIglesiaDTO {

    private Long idIglesia;
    private String nombreIglesia;
    private String direccion;
    private String denominacion;

    public static OtraIglesiaDTO fromEntity(OtraIglesiaDTO otraIglesiaDTO){
        return OtraIglesiaDTO.builder()
                .idIglesia(otraIglesiaDTO.getIdIglesia())
                .nombreIglesia(otraIglesiaDTO.getNombreIglesia())
                .direccion(otraIglesiaDTO.getDireccion())
                .denominacion(otraIglesiaDTO.getDenominacion())
                .build();
    }
}
