package com.codigo.sanidaddivina.domain.port.out;

import java.util.Optional;

/**
 * Puerto de salida abstracto para integración con lector de huellas dactilares.
 * La implementación concreta depende del hardware disponible.
 */
public interface FingerprintServicePort {
    /**
     * Identifica a una persona por su huella dactilar.
     * @param fingerprintTemplate Template de huella en Base64 desde el escáner
     * @return DNI de la persona si es reconocida, vacío si no se reconoce
     */
    Optional<String> identificarPorHuella(String fingerprintTemplate);

    /**
     * Registra la huella dactilar de una persona.
     * @param dni DNI de la persona
     * @param fingerprintTemplate Datos biométricos a almacenar
     * @return true si el registro fue exitoso
     */
    boolean registrarHuella(String dni, String fingerprintTemplate);
}
