package com.codigo.sanidaddivina.domain.port.in.miembro;

import com.codigo.sanidaddivina.domain.model.Profesor;

public interface AsignarProfesorUseCase {
    Profesor ejecutar(Long miembroId);
}
