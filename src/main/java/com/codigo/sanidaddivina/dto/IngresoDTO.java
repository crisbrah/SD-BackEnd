package com.codigo.sanidaddivina.dto;
import com.codigo.sanidaddivina.entities.IngresoEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IngresoDTO {
    private Long idIngreso;
    private String concepto;
    private BigDecimal monto;
    private String metodoPago;
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


    public static IngresoDTO fromEntity(IngresoEntity ingresoEntity) {
        IngresoDTO ingresoDTO1 = IngresoDTO.builder()
                .idIngreso(ingresoEntity.getIdIngreso())
                .concepto(ingresoEntity.getConcepto())
                .monto(ingresoEntity.getMonto())
                .metodoPago(ingresoEntity.getMetodoPago() != null ? ingresoEntity.getMetodoPago().name() : null)
                .usuaCrea(ingresoEntity.getUsuaCrea())
                .dateCreate(ingresoEntity.getDateCreate())
                .usuaModif(ingresoEntity.getUsuaModif())
                .dateModif(ingresoEntity.getDateModif())
                .usuaDelet(ingresoEntity.getUsuaDelet())
                .dateDelet(ingresoEntity.getDateDelet())
                //relacion
                .build();

        // Verificar si la persona no es nula antes de acceder a su idPersona
        if (ingresoEntity.getPersona() != null) {
            ingresoDTO1.setIdPersona(String.valueOf(ingresoEntity.getPersona().getIdPersona()));
        }

        // Verificar si la célula no es nula antes de acceder a su idCelula
        if (ingresoEntity.getMiembro() != null) {
            ingresoDTO1.setIdMiembro(String.valueOf(ingresoEntity.getMiembro().getIdMiembro()));
        }
        return ingresoDTO1;
    }

}
