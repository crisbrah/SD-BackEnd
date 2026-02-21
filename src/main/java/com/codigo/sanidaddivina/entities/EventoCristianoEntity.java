package com.codigo.sanidaddivina.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "evento_cristiano")
@Getter
@Setter
public class EventoCristianoEntity extends AuditoriaEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Long idEvento;
    @Column(name = "nombre_evento", nullable = false, length = 150)
    private String nombreEvento;
    @Column(name = "estado_evento")
    private boolean estadoEvento;
    //  @JsonIgnore
    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonaEventoEntity> personas;
}
