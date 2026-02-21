package com.codigo.sanidaddivina.domain.port.in.miembro;

import com.codigo.sanidaddivina.domain.model.MiembroMinisterio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Optional;

public interface AsignarMinisterioUseCase {
    MiembroMinisterio asignar(Command command);
    Optional<MiembroMinisterio> buscarPorId(Long id);
    Page<MiembroMinisterio> buscarPorMinisterio(Long ministerioId, Pageable pageable);
    Page<MiembroMinisterio> buscarPorMiembro(Long miembroId, Pageable pageable);
    void remover(Long id);

    record Command(Long miembroId, Long ministerioId, Date fechaIngreso, Date fechaSalida) {}
}
