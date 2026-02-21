package com.codigo.sanidaddivina.domain.port.in.persona;

import com.codigo.sanidaddivina.domain.model.Persona;

import java.util.List;

public interface BuscarCumpleaniosUseCase {
    List<Persona> hoy();
    List<Persona> porMes(int mes);
    List<Persona> proximos(int diasAdelante);
}
