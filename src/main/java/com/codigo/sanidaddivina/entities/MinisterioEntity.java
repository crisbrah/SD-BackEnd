package com.codigo.sanidaddivina.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ministerio")
@Getter
@Setter

public class MinisterioEntity extends AuditoriaEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ministerio")
    private Long idMinisterio;
    @Column(name = "nombre_ministerio", nullable = false, length = 150)
    private String nombreMinisterio;
    @Column(name = "estado_Ministerio")
    private boolean estadoMinisterio;
}
