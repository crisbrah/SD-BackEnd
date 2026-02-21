package com.codigo.sanidaddivina.entities;

import com.codigo.sanidaddivina.domain.model.enums.TipoCelula;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "celula")
@Getter
@Setter
public class CelulaEntity extends AuditoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_celula")
    private Long idCelula;

    @Column(name = "nombre_celula", nullable = false, length = 150)
    private String nombreCelula;

    @Column(name = "tipo_celula", length = 50)
    @Enumerated(EnumType.STRING)
    private TipoCelula tipoCelula;

    @Column(name = "estado_celula", nullable = false)
    private boolean estadoCelula;
}
