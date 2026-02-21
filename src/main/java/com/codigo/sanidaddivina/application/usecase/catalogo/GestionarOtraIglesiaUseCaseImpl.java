package com.codigo.sanidaddivina.application.usecase.catalogo;

import com.codigo.sanidaddivina.domain.model.OtraIglesia;
import com.codigo.sanidaddivina.domain.port.in.catalogo.GestionarOtraIglesiaUseCase;
import com.codigo.sanidaddivina.domain.port.out.OtraIglesiaRepositoryPort;
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
public class GestionarOtraIglesiaUseCaseImpl implements GestionarOtraIglesiaUseCase {

    private final OtraIglesiaRepositoryPort otraIglesiaRepository;

    @Override
    public OtraIglesia crear(Command command) {
        OtraIglesia otraIglesia = OtraIglesia.builder()
                .nombreIglesia(command.nombreIglesia())
                .direccion(command.direccion())
                .denominacion(command.denominacion())
                .build();
        return otraIglesiaRepository.guardar(otraIglesia);
    }

    @Override
    public Optional<OtraIglesia> buscarPorId(Long id) {
        return otraIglesiaRepository.buscarPorId(id);
    }

    @Override
    public Page<OtraIglesia> listar(Pageable pageable) {
        return otraIglesiaRepository.buscarTodos(pageable);
    }

    @Override
    public OtraIglesia actualizar(Long id, Command command) {
        OtraIglesia otraIglesia = otraIglesiaRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Iglesia no encontrada con ID: " + id));
        otraIglesia.setNombreIglesia(command.nombreIglesia());
        otraIglesia.setDireccion(command.direccion());
        otraIglesia.setDenominacion(command.denominacion());
        return otraIglesiaRepository.guardar(otraIglesia);
    }

    @Override
    public void eliminar(Long id) {
        otraIglesiaRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Iglesia no encontrada con ID: " + id));
        otraIglesiaRepository.eliminar(id);
    }
}
