package com.codigo.sanidaddivina.entities;

import com.codigo.sanidaddivina.domain.model.enums.DedoHuella;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "huella_persona")
@Getter
@Setter
public class HuellaPersonaEntity extends AuditoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_huella")
    private Long idHuella;

    @Column(name = "template_huella", nullable = false)
    private byte[] templateHuella;

    @Column(name = "dedo", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private DedoHuella dedo;

    @Column(name = "activo", nullable = false)
    private boolean activo;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona", nullable = false)
    private PersonaEntity persona;
}
