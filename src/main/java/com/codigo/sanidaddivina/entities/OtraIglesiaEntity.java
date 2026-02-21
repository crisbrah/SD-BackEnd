package com.codigo.sanidaddivina.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "otra_iglesia")
@Getter
@Setter

public class OtraIglesiaEntity extends AuditoriaEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_iglesia")
    private Long idIglesia;
    @Column(name = "nombre_iglesia", nullable = false, length = 150)
    private String nombreIglesia;
    @Column(name = "direccion", length = 150)
    private String direccion;
    @Column(name = "denominacion", nullable = false, length = 150)
    private String denominacion;
}
