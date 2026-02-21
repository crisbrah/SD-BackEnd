package com.codigo.sanidaddivina.application.usecase.financiero;

import com.codigo.sanidaddivina.domain.model.Egreso;
import com.codigo.sanidaddivina.domain.port.in.financiero.RegistrarEgresoUseCase;
import com.codigo.sanidaddivina.domain.port.out.EgresoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrarEgresoUseCaseImpl implements RegistrarEgresoUseCase {

    private final EgresoRepositoryPort egresoRepository;

    @Override
    public Egreso ejecutar(Command command) {
        Egreso egreso = Egreso.builder()
                .concepto(command.concepto())
                .montoSalida(command.montoSalida())
                .tipoEgreso(command.tipoEgreso())
                .metodoSalida(command.metodoSalida())
                .fechaEgreso(command.fechaEgreso())
                .personaId(command.personaId())
                .miembroId(command.miembroId())
                .build();

        return egresoRepository.guardar(egreso);
    }
}
