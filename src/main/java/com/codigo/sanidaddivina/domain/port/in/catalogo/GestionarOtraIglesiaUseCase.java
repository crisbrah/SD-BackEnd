package com.codigo.sanidaddivina.domain.port.in.catalogo;

import com.codigo.sanidaddivina.domain.model.OtraIglesia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface GestionarOtraIglesiaUseCase {
    OtraIglesia crear(Command command);
    Optional<OtraIglesia> buscarPorId(Long id);
    Page<OtraIglesia> listar(Pageable pageable);
    OtraIglesia actualizar(Long id, Command command);
    void eliminar(Long id);

    record Command(String nombreIglesia, String direccion, String denominacion) {}
}
