package com.codigo.sanidaddivina.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rol")
@Getter
@Setter

public class RolEntity extends AuditoriaEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long idRol;
    @Column(name = "nombre_rol", nullable = false, length = 150, unique = true)
    private String nombreRol;
    @Column(name = "estado_rol", nullable = false)
    private boolean estadoRol;

}
