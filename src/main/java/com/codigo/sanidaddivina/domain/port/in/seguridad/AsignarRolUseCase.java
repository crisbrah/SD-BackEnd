package com.codigo.sanidaddivina.domain.port.in.seguridad;

import com.codigo.sanidaddivina.domain.model.MiembroRol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Optional;

public interface AsignarRolUseCase {
    MiembroRol asignar(Command command);
    Optional<MiembroRol> buscarPorId(Long id);
    Page<MiembroRol> buscarPorMiembro(Long miembroId, Pageable pageable);
    void revocar(Long id);

    record Command(Long miembroId, Long rolId, Date inicioRol, Date finRol) {}
}
