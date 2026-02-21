package com.codigo.sanidaddivina.seguridad.impl;

import com.codigo.sanidaddivina.dao.MiembroRepository;
import com.codigo.sanidaddivina.dao.PersonaRepository;
import com.codigo.sanidaddivina.dao.RolRepository;
import com.codigo.sanidaddivina.entities.MiembroEntity;
import com.codigo.sanidaddivina.entities.PersonaEntity;
import com.codigo.sanidaddivina.infrastructure.exception.BusinessException;
import com.codigo.sanidaddivina.infrastructure.exception.DuplicateResourceException;
import com.codigo.sanidaddivina.request.SignInRequest;
import com.codigo.sanidaddivina.request.SignUpRequest;
import com.codigo.sanidaddivina.response.AuthenticationResponse;
import com.codigo.sanidaddivina.response.SignUpResponse;
import com.codigo.sanidaddivina.seguridad.AuthenticationService;
import com.codigo.sanidaddivina.seguridad.JWTService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final MiembroRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final RolRepository rolRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public AuthenticationResponse signin(SignInRequest signInRequest) {
        // Verificar aprobación antes de autenticar (evita "Cuenta desactivada" genérico)
        var user = usuarioRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email no válido"));

        if (!user.isAprobado()) {
            throw new BusinessException("Tu cuenta está pendiente de aprobación. Un administrador debe activarla.");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getEmail(), signInRequest.getPassword()));

        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(Map.of(), user);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(jwt);
        authenticationResponse.setRefreshToken(refreshToken);
        return authenticationResponse;
    }

    @Override
    @Transactional
    public SignUpResponse signup(SignUpRequest req) {
        if (usuarioRepository.existsByEmail(req.getEmail())) {
            throw new DuplicateResourceException("Ya existe una cuenta con ese correo electrónico.");
        }

        PersonaEntity persona = new PersonaEntity();
        persona.setNombres(req.getNombres());
        persona.setApePat(req.getApePat());
        persona.setApeMat(req.getApeMat());
        persona.setDni(req.getDni());
        persona.setFechaNacimiento(req.getFechaNacimiento());
        persona.setEstado(true);
        personaRepository.save(persona);

        MiembroEntity miembro = new MiembroEntity();
        miembro.setEmail(req.getEmail());
        miembro.setPassword(passwordEncoder.encode(req.getPassword()));
        miembro.setPersona(persona);
        miembro.setFechaConversion(LocalDate.now());
        miembro.setEstadoMiembro(true);
        miembro.setEsNuevo(true);
        miembro.setAprobado(false); // Pendiente de aprobación del admin
        usuarioRepository.save(miembro);

        SignUpResponse response = new SignUpResponse();
        response.setStatus("PENDING");
        response.setMensaje("Tu solicitud de acceso fue enviada. Un administrador revisará y aprobará tu cuenta.");
        return response;
    }
}
