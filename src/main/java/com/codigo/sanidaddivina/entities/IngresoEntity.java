package com.codigo.sanidaddivina.entities;

import com.codigo.sanidaddivina.domain.model.enums.MetodoPago;
import com.codigo.sanidaddivina.domain.model.enums.TipoIngreso;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "ingreso")
@Getter
@Setter
public class IngresoEntity extends AuditoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ingreso")
    private Long idIngreso;

    @Column(name = "concepto", nullable = false, length = 200)
    private String concepto;

    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(name = "tipo_ingreso")
    @Enumerated(EnumType.STRING)
    private TipoIngreso tipoIngreso;

    @Column(name = "metodo_pago", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona")
    private PersonaEntity persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_miembro")
    private MiembroEntity miembro;
}
