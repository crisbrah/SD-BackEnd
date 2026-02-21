package com.codigo.sanidaddivina.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Builder
public class MiembroMinisterioDTO {

    private Long idMiembroMinisterio;
    private Date fechaIngresoMinisterio;
    private Date fechaSalidaMinisterio;
    //  Relacion con miembro
    private String idMiembro;
    //Relacion con Ministerio
    private String idMinisterio;

    public static MiembroMinisterioDTO fromEntity(MiembroMinisterioDTO miembroMinisterioDTO)
    {
        return MiembroMinisterioDTO.builder()
                .idMiembroMinisterio(miembroMinisterioDTO.getIdMiembroMinisterio())
                .fechaIngresoMinisterio(miembroMinisterioDTO.getFechaIngresoMinisterio())
                .fechaSalidaMinisterio(miembroMinisterioDTO.getFechaSalidaMinisterio())
                //relacion
                .idMiembro(miembroMinisterioDTO.getIdMiembro())
                .idMinisterio(miembroMinisterioDTO.getIdMinisterio())
                .build();
    }
}
