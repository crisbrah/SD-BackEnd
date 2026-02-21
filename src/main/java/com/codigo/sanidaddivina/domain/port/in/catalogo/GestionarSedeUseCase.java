package com.codigo.sanidaddivina.domain.port.in.catalogo;

import com.codigo.sanidaddivina.domain.model.Sede;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface GestionarSedeUseCase {
    Sede crear(Command command);
    Optional<Sede> buscarPorId(Long id);
    Page<Sede> listar(Pageable pageable);
    Sede actualizar(Long id, Command command);
    void eliminar(Long id);

    record Command(String nombreSede, String direccion, String distrito, String ciudad, String pais) {}
}
