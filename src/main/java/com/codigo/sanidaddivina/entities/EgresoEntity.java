package com.codigo.sanidaddivina.entities;

import com.codigo.sanidaddivina.domain.model.enums.MetodoPago;
import com.codigo.sanidaddivina.domain.model.enums.TipoEgreso;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "egreso")
@Getter
@Setter
public class EgresoEntity extends AuditoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "concepto", nullable = false, length = 200)
    private String concepto;

    @Column(name = "monto_salida", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoSalida;

    @Column(name = "tipo_egreso")
    @Enumerated(EnumType.STRING)
    private TipoEgreso tipoEgreso;

    @Column(name = "metodo_salida", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private MetodoPago metodoSalida;

    @Column(name = "fecha_egreso")
    private LocalDate fechaEgreso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona")
    private PersonaEntity persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_miembro")
    private MiembroEntity miembro;
}
