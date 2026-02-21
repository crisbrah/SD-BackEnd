package com.codigo.sanidaddivina.domain.port.out;

import com.codigo.sanidaddivina.domain.model.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PersonaRepositoryPort {
    Persona guardar(Persona persona);
    Optional<Persona> buscarPorId(Long id);
    Optional<Persona> buscarPorDni(String dni);
    Page<Persona> buscarPorNombre(String nombre, Pageable pageable);
    Page<Persona> buscarTodos(Pageable pageable);
    List<Persona> buscarCumpleaniosHoy();
    List<Persona> buscarCumpleaniosPorMes(int mes);
    List<Persona> buscarCumpleaniosProximos(int diasAdelante);
    boolean existePorDni(String dni);
    void eliminar(Long id);
}
