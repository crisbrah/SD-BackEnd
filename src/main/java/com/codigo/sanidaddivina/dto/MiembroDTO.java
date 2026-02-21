package com.codigo.sanidaddivina.dto;
import com.codigo.sanidaddivina.entities.MiembroEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MiembroDTO {
    private Long idMiembro;
    private LocalDate fechaConversion;
    private boolean estadoMiembro;
    private String email;
    private String password;
    private String usuaCrea;
    private Timestamp dateCreate;
    private String usuaModif;
    private Timestamp dateModif;
    private String usuaDelet;
    private Timestamp dateDelet;
    //Relacion con Persona
    private Long idPersona;
    //Relacion con Celula
    private Long idCelula;

    public static MiembroDTO fromEntity(MiembroEntity miembroEntity) {
        MiembroDTO miembroDTO = MiembroDTO.builder()
                .idMiembro(miembroEntity.getIdMiembro())
                .fechaConversion(miembroEntity.getFechaConversion())
                .estadoMiembro(miembroEntity.isEstadoMiembro())
                .usuaCrea(miembroEntity.getUsuaCrea())
                .email(miembroEntity.getEmail())
                .password(miembroEntity.getPassword())
                .dateCreate(miembroEntity.getDateCreate())
                .usuaModif(miembroEntity.getUsuaModif())
                .dateModif(miembroEntity.getDateModif())
                .usuaDelet(miembroEntity.getUsuaDelet())
                .dateDelet(miembroEntity.getDateDelet())
                .build();

        // Verificar si la persona no es nula antes de acceder a su idPersona
        if (miembroEntity.getPersona() != null) {
            miembroDTO.setIdPersona(miembroEntity.getPersona().getIdPersona());
        }

        // Verificar si la célula no es nula antes de acceder a su idCelula
        if (miembroEntity.getCelula() != null) {
            miembroDTO.setIdCelula(miembroEntity.getCelula().getIdCelula());
        }

        return miembroDTO;
    }

}
