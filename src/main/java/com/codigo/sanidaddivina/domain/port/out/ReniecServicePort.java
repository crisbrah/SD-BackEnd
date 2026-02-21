package com.codigo.sanidaddivina.domain.port.out;

import java.util.Optional;

public interface ReniecServicePort {
    Optional<ReniecData> consultarPorDni(String dni);

    record ReniecData(String nombres, String apellidoPaterno, String apellidoMaterno) {}
}
