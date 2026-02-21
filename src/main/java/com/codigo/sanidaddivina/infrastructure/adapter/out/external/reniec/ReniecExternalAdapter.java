package com.codigo.sanidaddivina.infrastructure.adapter.out.external.reniec;

import com.codigo.sanidaddivina.client.ClientReniec;
import com.codigo.sanidaddivina.domain.port.out.ReniecServicePort;
import com.codigo.sanidaddivina.dto.ReniecDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReniecExternalAdapter implements ReniecServicePort {

    private final ClientReniec clientReniec;

    @Value("${token.reniec}")
    private String tokenReniec;

    @Override
    public Optional<ReniecData> consultarPorDni(String dni) {
        try {
            ReniecDTO dto = clientReniec.getInfoReniec(dni, "Bearer " + tokenReniec);
            if (dto == null || dto.getNombres() == null) {
                return Optional.empty();
            }
            return Optional.of(new ReniecData(
                    dto.getNombres(),
                    dto.getApellidoPaterno(),
                    dto.getApellidoMaterno()
            ));
        } catch (Exception e) {
            log.warn("Error consultando RENIEC para DNI {}: {}", dni, e.getMessage());
            return Optional.empty();
        }
    }
}
