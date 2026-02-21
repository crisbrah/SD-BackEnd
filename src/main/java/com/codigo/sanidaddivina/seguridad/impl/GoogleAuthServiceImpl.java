package com.codigo.sanidaddivina.seguridad.impl;

import com.codigo.sanidaddivina.dao.MiembroRepository;
import com.codigo.sanidaddivina.dao.PersonaRepository;
import com.codigo.sanidaddivina.entities.MiembroEntity;
import com.codigo.sanidaddivina.entities.PersonaEntity;
import com.codigo.sanidaddivina.infrastructure.exception.BusinessException;
import com.codigo.sanidaddivina.infrastructure.exception.DuplicateResourceException;
import com.codigo.sanidaddivina.request.GoogleSignUpRequest;
import com.codigo.sanidaddivina.response.GoogleAuthResponse;
import com.codigo.sanidaddivina.response.SignUpResponse;
import com.codigo.sanidaddivina.seguridad.GoogleAuthService;
import com.codigo.sanidaddivina.seguridad.JWTService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoogleAuthServiceImpl implements GoogleAuthService {

    private final MiembroRepository miembroRepository;
    private final PersonaRepository personaRepository;
    private final JWTService jwtService;

    @Value("${google.client-id}")
    private String googleClientId;

    private static final String TOKENINFO_URL =
            "https://oauth2.googleapis.com/tokeninfo?id_token=";

    // ── Login con Google ──────────────────────────────────────────────────────

    @Override
    public GoogleAuthResponse loginWithGoogle(String idToken) {
        Map<String, Object> tokenInfo = verifyGoogleToken(idToken);
        String email = (String) tokenInfo.get("email");
        String nombre = buildNombre(tokenInfo);

        Optional<MiembroEntity> miembroOpt = miembroRepository.findByEmail(email);

        if (miembroOpt.isEmpty()) {
            // Usuario nuevo — necesita completar su perfil
            return GoogleAuthResponse.builder()
                    .status("NEW")
                    .email(email)
                    .nombres(nombre)
                    .build();
        }

        MiembroEntity miembro = miembroOpt.get();

        if (!miembro.isAprobado()) {
            return GoogleAuthResponse.builder()
                    .status("PENDING")
                    .build();
        }

        // Usuario aprobado — generar JWT
        String jwt = jwtService.generateToken(miembro);
        String refresh = jwtService.generateRefreshToken(Map.of(), miembro);

        return GoogleAuthResponse.builder()
                .status("APPROVED")
                .token(jwt)
                .refreshToken(refresh)
                .build();
    }

    // ── Registro con Google (completar perfil) ────────────────────────────────

    @Override
    @Transactional
    public SignUpResponse signupWithGoogle(GoogleSignUpRequest req) {
        // Re-verificar el token de Google
        Map<String, Object> tokenInfo = verifyGoogleToken(req.getIdToken());
        String emailFromToken = (String) tokenInfo.get("email");

        // El email del formulario debe coincidir con el del token
        if (!emailFromToken.equalsIgnoreCase(req.getEmail())) {
            throw new BusinessException("El correo no coincide con la cuenta de Google.");
        }

        if (miembroRepository.existsByEmail(req.getEmail())) {
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
        miembro.setPassword(null); // Login solo por Google
        miembro.setPersona(persona);
        miembro.setFechaConversion(LocalDate.now());
        miembro.setEstadoMiembro(true);
        miembro.setEsNuevo(true);
        miembro.setAprobado(false); // Pendiente de aprobación
        miembroRepository.save(miembro);

        SignUpResponse response = new SignUpResponse();
        response.setStatus("PENDING");
        response.setMensaje("Tu solicitud de acceso fue enviada. Un administrador revisará y aprobará tu cuenta.");
        return response;
    }

    // ── Utilidades ────────────────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    private Map<String, Object> verifyGoogleToken(String idToken) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> tokenInfo;
        try {
            tokenInfo = restTemplate.getForObject(TOKENINFO_URL + idToken, Map.class);
        } catch (Exception e) {
            throw new BusinessException("Token de Google inválido o expirado.");
        }

        if (tokenInfo == null) {
            throw new BusinessException("No se pudo verificar el token de Google.");
        }

        // Verificar que el token fue emitido para nuestra app
        String aud = (String) tokenInfo.get("aud");
        if (!googleClientId.equals(aud)) {
            throw new BusinessException("Token de Google no válido para esta aplicación.");
        }

        return tokenInfo;
    }

    private String buildNombre(Map<String, Object> tokenInfo) {
        String given = (String) tokenInfo.getOrDefault("given_name", "");
        String family = (String) tokenInfo.getOrDefault("family_name", "");
        return (given + " " + family).trim();
    }
}
