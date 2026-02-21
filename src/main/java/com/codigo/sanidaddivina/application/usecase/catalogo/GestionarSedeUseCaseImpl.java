package com.codigo.sanidaddivina.application.usecase.catalogo;

import com.codigo.sanidaddivina.domain.model.Sede;
import com.codigo.sanidaddivina.domain.port.in.catalogo.GestionarSedeUseCase;
import com.codigo.sanidaddivina.domain.port.out.SedeRepositoryPort;
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
public class GestionarSedeUseCaseImpl implements GestionarSedeUseCase {

    private final SedeRepositoryPort sedeRepository;

    @Override
    public Sede crear(Command command) {
        Sede sede = Sede.builder()
                .nombreSede(command.nombreSede())
                .direccion(command.direccion())
                .distrito(command.distrito())
                .ciudad(command.ciudad())
                .pais(command.pais())
                .build();
        return sedeRepository.guardar(sede);
    }

    @Override
    public Optional<Sede> buscarPorId(Long id) {
        return sedeRepository.buscarPorId(id);
    }

    @Override
    public Page<Sede> listar(Pageable pageable) {
        return sedeRepository.buscarTodos(pageable);
    }

    @Override
    public Sede actualizar(Long id, Command command) {
        Sede sede = sedeRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sede no encontrada con ID: " + id));
        sede.setNombreSede(command.nombreSede());
        sede.setDireccion(command.direccion());
        sede.setDistrito(command.distrito());
        sede.setCiudad(command.ciudad());
        sede.setPais(command.pais());
        return sedeRepository.guardar(sede);
    }

    @Override
    public void eliminar(Long id) {
        sedeRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sede no encontrada con ID: " + id));
        sedeRepository.eliminar(id);
    }
}
