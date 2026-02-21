package com.codigo.sanidaddivina.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SedeDTO {
    private Long idSede;
    private String nombreSede;
    private String direccion;
    private String distrito;
    private String ciudad;
    private String pais;
    public static SedeDTO fromEntity(SedeDTO sedeDTO){
        return SedeDTO.builder()
                .idSede(sedeDTO.getIdSede())
                .nombreSede(sedeDTO.getNombreSede())
                .direccion(sedeDTO.getDireccion())
                .distrito(sedeDTO.getDistrito())
                .ciudad(sedeDTO.getCiudad())
                .pais(sedeDTO.getPais())
                .build();
    }
}
