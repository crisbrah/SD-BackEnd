package com.codigo.sanidaddivina.infrastructure.adapter.out.external.fingerprint;

import com.codigo.sanidaddivina.domain.port.out.FingerprintServicePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Implementación stub del lector de huellas.
 * Se activa cuando fingerprint.enabled=false (por defecto).
 * Reemplazar con la implementación real del SDK del escáner cuando esté disponible.
 */
@Component
@ConditionalOnProperty(name = "fingerprint.enabled", havingValue = "false", matchIfMissing = true)
@Slf4j
public class FingerprintAdapterStub implements FingerprintServicePort {

    @Override
    public Optional<String> identificarPorHuella(String fingerprintTemplate) {
        log.warn("Lector de huellas no conectado (modo STUB). Identificación no disponible.");
        return Optional.empty();
    }

    @Override
    public boolean registrarHuella(String dni, String fingerprintTemplate) {
        log.warn("Lector de huellas no conectado (modo STUB). Registro de huella omitido para DNI: {}", dni);
        return false;
    }
}
