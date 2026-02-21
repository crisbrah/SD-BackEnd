package com.codigo.sanidaddivina.application.usecase.asistencia;

import com.codigo.sanidaddivina.domain.model.SesionCulto;
import com.codigo.sanidaddivina.domain.port.in.asistencia.GestionarSesionCultoUseCase;
import com.codigo.sanidaddivina.domain.port.out.SesionCultoRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.exception.BusinessException;
import com.codigo.sanidaddivina.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GestionarSesionCultoUseCaseImpl implements GestionarSesionCultoUseCase {

    private final SesionCultoRepositoryPort sesionCultoRepository;

    @Override
    public SesionCulto abrir(Command command) {
        if (!sesionCultoRepository.listarSesionesAbiertas().isEmpty()) {
            throw new BusinessException(
                    "Ya existe una sesión de culto abierta. Ciérrela antes de abrir una nueva.");
        }

        SesionCulto sesion = SesionCulto.builder()
                .nombreSesion(command.nombreSesion())
                .tipoCulto(command.tipoCulto())
                .fechaInicio(LocalDateTime.now())
                .abierta(true)
                .sedeId(command.sedeId())
                .build();

        return sesionCultoRepository.guardar(sesion);
    }

    @Override
    public SesionCulto cerrar(Long id) {
        SesionCulto sesion = sesionCultoRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Sesión de culto no encontrada con ID: " + id));

        if (!sesion.isAbierta()) {
            throw new BusinessException("La sesión ya está cerrada.");
        }

        sesion.setAbierta(false);
        sesion.setFechaFin(LocalDateTime.now());

        return sesionCultoRepository.guardar(sesion);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SesionCulto> buscarPorId(Long id) {
        return sesionCultoRepository.buscarPorId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SesionCulto> buscarSesionAbierta() {
        return sesionCultoRepository.buscarSesionAbierta();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SesionCulto> listarSesionesAbiertas() {
        return sesionCultoRepository.listarSesionesAbiertas();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SesionCulto> buscarTodos(Pageable pageable) {
        return sesionCultoRepository.buscarTodos(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SesionCulto> buscarPorSede(Long sedeId, Pageable pageable) {
        return sesionCultoRepository.buscarPorSede(sedeId, pageable);
    }

    @Override
    public void eliminar(Long id) {
        SesionCulto sesion = sesionCultoRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Sesión de culto no encontrada con ID: " + id));

        if (sesion.isAbierta()) {
            throw new BusinessException(
                    "No se puede eliminar una sesión abierta. Ciérrela primero.");
        }

        sesionCultoRepository.eliminar(id);
    }
}
