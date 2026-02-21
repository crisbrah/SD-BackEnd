package com.codigo.sanidaddivina.dto;
import com.codigo.sanidaddivina.entities.PersonaEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonaDTO {
    private Long idPersona;
    private String nombres;
    private String apePat;
    private String apeMat;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;
    private String lugarNacimiento;
    private String esCivil;
    private int numHijos;
    private String dni;
    private String ocupacion;
    private String direccion;
    private String distrito;
    private String provincia;
    private String departamento;
    private String email;
    private boolean estado;
    private String usuaCrea;
    private Timestamp dateCreate;
    private String usuaModif;
    private Timestamp dateModif;
    private String usuaDelet;
    private Timestamp dateDelet;
    //Relacion a la tabla OtraIglesia
    private Long idIglesia;
    public static PersonaDTO fromEntity(PersonaEntity entity) {
        return PersonaDTO.builder()
                .idPersona(entity.getIdPersona())
                .nombres(entity.getNombres())
                .apePat(entity.getApePat())
                .apeMat(entity.getApeMat())
                .fechaNacimiento(entity.getFechaNacimiento())
                .lugarNacimiento(entity.getLugarNacimiento())
                .esCivil(entity.getEsCivil())
                .numHijos(entity.getNumHijos())
                .dni(entity.getDni())
                .ocupacion(entity.getOcupacion())
                .direccion(entity.getDireccion())
                .distrito(entity.getDistrito())
                .provincia(entity.getProvincia())
                .departamento(entity.getDepartamento())
                .estado(entity.isEstado())
                .usuaCrea(entity.getUsuaCrea())
                .dateCreate(entity.getDateCreate())
                .usuaModif(entity.getUsuaModif())
                .dateModif(entity.getDateModif())
                .usuaDelet(entity.getUsuaDelet())
                .dateDelet(entity.getDateDelet())
                .build();
    }
}
