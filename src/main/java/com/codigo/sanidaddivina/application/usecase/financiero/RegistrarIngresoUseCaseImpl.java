package com.codigo.sanidaddivina.application.usecase.financiero;

import com.codigo.sanidaddivina.domain.model.Ingreso;
import com.codigo.sanidaddivina.domain.port.in.financiero.RegistrarIngresoUseCase;
import com.codigo.sanidaddivina.domain.port.out.IngresoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrarIngresoUseCaseImpl implements RegistrarIngresoUseCase {

    private final IngresoRepositoryPort ingresoRepository;

    @Override
    public Ingreso ejecutar(Command command) {
        Ingreso ingreso = Ingreso.builder()
                .concepto(command.concepto())
                .monto(command.monto())
                .tipoIngreso(command.tipoIngreso())
                .metodoPago(command.metodoPago())
                .fechaIngreso(command.fechaIngreso())
                .personaId(command.personaId())
                .miembroId(command.miembroId())
                .build();

        return ingresoRepository.guardar(ingreso);
    }
}
