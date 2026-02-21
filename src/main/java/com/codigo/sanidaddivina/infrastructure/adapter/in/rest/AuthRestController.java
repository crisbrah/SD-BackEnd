package com.codigo.sanidaddivina.infrastructure.adapter.in.rest;

import com.codigo.sanidaddivina.dao.MiembroRepository;
import com.codigo.sanidaddivina.dao.RolRepository;
import com.codigo.sanidaddivina.domain.port.out.ReniecServicePort;
import com.codigo.sanidaddivina.infrastructure.exception.ApiResponse;
import com.codigo.sanidaddivina.request.GoogleAuthRequest;
import com.codigo.sanidaddivina.request.GoogleSignUpRequest;
import com.codigo.sanidaddivina.request.SignInRequest;
import com.codigo.sanidaddivina.request.SignUpRequest;
import com.codigo.sanidaddivina.response.AuthenticationResponse;
import com.codigo.sanidaddivina.seguridad.AuthenticationService;
import com.codigo.sanidaddivina.seguridad.GoogleAuthService;
import com.codigo.sanidaddivina.seguridad.JWTService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Login, registro y renovación de tokens")
public class AuthRestController {

    private final AuthenticationService authenticationService;
    private final GoogleAuthService googleAuthService;
    private final JWTService jwtService;
    private final MiembroRepository miembroRepository;
    private final RolRepository rolRepository;
    private final ReniecServicePort reniecServicePort;

    public record RefreshTokenRequest(@NotBlank String refreshToken) {}

    // ── Email / Password ──────────────────────────────────────────────────────

    @PostMapping("/signup")
    @Operation(summary = "Registrar nuevo miembro (queda pendiente de aprobación)")
    public ResponseEntity<ApiResponse<?>> signup(@Valid @RequestBody SignUpRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(authenticationService.signup(request)));
    }

    @PostMapping("/signin")
    @Operation(summary = "Iniciar sesión con email y contraseña")
    public ResponseEntity<ApiResponse<?>> signin(@Valid @RequestBody SignInRequest request) {
        AuthenticationResponse response = authenticationService.signin(request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Renovar access token usando refresh token")
    public ResponseEntity<ApiResponse<?>> refreshToken(@Valid @RequestBody RefreshTokenRequest req) {
        String email = jwtService.extractUsername(req.refreshToken());
        var user = miembroRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!jwtService.validateToken(req.refreshToken(), user)) {
            return ResponseEntity.status(401)
                    .body(ApiResponse.error("Refresh token inválido o expirado", 401));
        }

        String newToken = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(Map.of(), user);

        AuthenticationResponse response = new AuthenticationResponse();
        response.setToken(newToken);
        response.setRefreshToken(newRefreshToken);

        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    // ── Google OAuth ──────────────────────────────────────────────────────────

    @PostMapping("/google")
    @Operation(summary = "Iniciar sesión con Google — retorna APPROVED / PENDING / NEW")
    public ResponseEntity<ApiResponse<?>> googleLogin(@Valid @RequestBody GoogleAuthRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(googleAuthService.loginWithGoogle(request.getIdToken())));
    }

    @PostMapping("/google/signup")
    @Operation(summary = "Completar registro de usuario que ingresó con Google (queda pendiente de aprobación)")
    public ResponseEntity<ApiResponse<?>> googleSignup(@Valid @RequestBody GoogleSignUpRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(googleAuthService.signupWithGoogle(request)));
    }

    // ── Bootstrap: promueve una cuenta a SUPER_ADMIN (solo cuando no hay ninguno) ──

    @PostMapping("/bootstrap")
    @Operation(summary = "Promueve un usuario a SUPER_ADMIN (solo funciona cuando no existe ninguno)")
    @jakarta.transaction.Transactional
    public ResponseEntity<ApiResponse<Object>> bootstrap(@RequestParam String email) {
        if (miembroRepository.existsByRoles_NombreRol("SUPER_ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.error("Ya existe un super administrador en el sistema.", 403));
        }
        var miembro = miembroRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No se encontró ningún usuario con ese email."));
        var rol = rolRepository.findByNombreRol("SUPER_ADMIN")
                .orElseThrow(() -> new RuntimeException("Rol SUPER_ADMIN no encontrado. Reinicia el servidor."));
        miembro.setAprobado(true);
        miembro.setEstadoMiembro(true);
        miembro.getRoles().add(rol);
        miembroRepository.save(miembro);
        Map<String, String> result = new java.util.LinkedHashMap<>();
        result.put("mensaje", "¡Super administrador configurado exitosamente!");
        result.put("email", email);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    // ── Consulta RENIEC pública (sin auth) ────────────────────────────────────

    @GetMapping("/reniec/{dni}")
    @Operation(summary = "Consultar datos de RENIEC por DNI (público, para registro)")
    public ResponseEntity<ApiResponse<Object>> consultarReniec(@PathVariable String dni) {
        if (reniecServicePort.consultarPorDni(dni).isPresent()) {
            var data = reniecServicePort.consultarPorDni(dni).get();
            Map<String, String> result = new java.util.LinkedHashMap<>();
            result.put("nombres",         data.nombres() != null ? data.nombres() : "");
            result.put("apellidoPaterno", data.apellidoPaterno() != null ? data.apellidoPaterno() : "");
            result.put("apellidoMaterno", data.apellidoMaterno() != null ? data.apellidoMaterno() : "");
            return ResponseEntity.ok(ApiResponse.ok(result));
        }
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
}
