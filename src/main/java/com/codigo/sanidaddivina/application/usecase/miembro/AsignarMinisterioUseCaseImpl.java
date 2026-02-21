package com.codigo.sanidaddivina.application.usecase.miembro;

import com.codigo.sanidaddivina.domain.model.MiembroMinisterio;
import com.codigo.sanidaddivina.domain.port.in.miembro.AsignarMinisterioUseCase;
import com.codigo.sanidaddivina.domain.port.out.MiembroMinisterioRepositoryPort;
import com.codigo.sanidaddivina.domain.port.out.MiembroRepositoryPort;
import com.codigo.sanidaddivina.domain.port.out.MinisterioRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AsignarMinisterioUseCaseImpl implements AsignarMinisterioUseCase {

    private final MiembroMinisterioRepositoryPort miembroMinisterioRepository;
    private final MiembroRepositoryPort miembroRepository;
    private final MinisterioRepositoryPort ministerioRepository;

    @Override
    public MiembroMinisterio asignar(Command command) {
        miembroRepository.buscarPorId(command.miembroId())
                .orElseThrow(() -> new ResourceNotFoundException("Miembro no encontrado con ID: " + command.miembroId()));
        ministerioRepository.buscarPorId(command.ministerioId())
                .orElseThrow(() -> new ResourceNotFoundException("Ministerio no encontrado con ID: " + command.ministerioId()));

        MiembroMinisterio miembroMinisterio = MiembroMinisterio.builder()
                .miembroId(command.miembroId())
                .ministerioId(command.ministerioId())
                .fechaIngresoMinisterio(command.fechaIngreso())
                .fechaSalidaMinisterio(command.fechaSalida())
                .build();
        return miembroMinisterioRepository.guardar(miembroMinisterio);
    }

    @Override
    public Optional<MiembroMinisterio> buscarPorId(Long id) {
        return miembroMinisterioRepository.buscarPorId(id);
    }

    @Override
    public Page<MiembroMinisterio> buscarPorMinisterio(Long ministerioId, Pageable pageable) {
        return miembroMinisterioRepository.buscarPorMinisterio(ministerioId, pageable);
    }

    @Override
    public Page<MiembroMinisterio> buscarPorMiembro(Long miembroId, Pageable pageable) {
        return miembroMinisterioRepository.buscarPorMiembro(miembroId, pageable);
    }

    @Override
    public void remover(Long id) {
        miembroMinisterioRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asignación de ministerio no encontrada con ID: " + id));
        miembroMinisterioRepository.eliminar(id);
    }
}
