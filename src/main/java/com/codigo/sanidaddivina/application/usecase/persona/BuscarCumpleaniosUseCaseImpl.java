package com.codigo.sanidaddivina.application.usecase.persona;

import com.codigo.sanidaddivina.domain.model.Persona;
import com.codigo.sanidaddivina.domain.port.in.persona.BuscarCumpleaniosUseCase;
import com.codigo.sanidaddivina.domain.port.out.PersonaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BuscarCumpleaniosUseCaseImpl implements BuscarCumpleaniosUseCase {

    private final PersonaRepositoryPort personaRepository;

    @Override
    public List<Persona> hoy() {
        return personaRepository.buscarCumpleaniosHoy();
    }

    @Override
    public List<Persona> porMes(int mes) {
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("El mes debe estar entre 1 y 12");
        }
        return personaRepository.buscarCumpleaniosPorMes(mes);
    }

    @Override
    public List<Persona> proximos(int diasAdelante) {
        if (diasAdelante < 1 || diasAdelante > 365) {
            throw new IllegalArgumentException("Los días deben estar entre 1 y 365");
        }
        return personaRepository.buscarCumpleaniosProximos(diasAdelante);
    }
}
