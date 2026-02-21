package com.codigo.sanidaddivina.application.usecase.miembro;

import com.codigo.sanidaddivina.domain.model.Miembro;
import com.codigo.sanidaddivina.domain.port.in.miembro.BuscarMiembroUseCase;
import com.codigo.sanidaddivina.domain.port.out.MiembroRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BuscarMiembroUseCaseImpl implements BuscarMiembroUseCase {

    private final MiembroRepositoryPort miembroRepository;

    @Override
    public Optional<Miembro> porId(Long id) {
        return miembroRepository.buscarPorId(id);
    }

    @Override
    public Page<Miembro> todos(Pageable pageable) {
        return miembroRepository.buscarTodos(pageable);
    }
}
