package com.codigo.sanidaddivina.entities;

import com.codigo.sanidaddivina.domain.model.enums.CargoIglesia;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "persona")
@Getter
@Setter
public class PersonaEntity extends AuditoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Long idPersona;

    @Column(name = "nombres", nullable = false, length = 150)
    private String nombres;

    @Column(name = "apellido_paterno", nullable = false, length = 100)
    private String apePat;

    @Column(name = "apellido_materno", nullable = false, length = 100)
    private String apeMat;

    @Past
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "fecha_bautizo")
    private LocalDate fechaBautizo;

    @Column(name = "celular", length = 15)
    private String celular;

    @Column(name = "lugar_nacimiento", length = 100)
    private String lugarNacimiento;

    @Column(name = "estado_civil", length = 50)
    private String esCivil;

    @Column(name = "numero_hijos")
    private int numHijos;

    @Column(name = "dni", nullable = false, length = 8, unique = true)
    private String dni;

    @Column(name = "ocupacion")
    private String ocupacion;

    @Column(name = "direccion", length = 150)
    private String direccion;

    @Column(name = "distrito")
    private String distrito;

    @Column(name = "provincia")
    private String provincia;

    @Column(name = "departamento")
    private String departamento;

    @Column(name = "cargo")
    @Enumerated(EnumType.STRING)
    private CargoIglesia cargo;

    @Column(name = "estado")
    private boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_iglesia_procedencia")
    private OtraIglesiaEntity iglesiaProcedencia;

    @OneToOne(mappedBy = "persona")
    private MiembroEntity miembro;
}
