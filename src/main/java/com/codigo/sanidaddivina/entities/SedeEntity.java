package com.codigo.sanidaddivina.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sede")
@Getter
@Setter

public class SedeEntity extends AuditoriaEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sede")
    private Long idSede;
    @Column(name = "nombre_sede", nullable = false, length = 150)
    private String nombreSede;
    @Column(name = "direccion", length = 150)
    private String direccion;
    @Column(name = "distrito")
    private String distrito;
    @Column(name = "ciudad")
    private String ciudad;
    @Column(name = "pais")
    private String pais;
}
