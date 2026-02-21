package com.codigo.sanidaddivina.seguridad;

import com.codigo.sanidaddivina.request.GoogleSignUpRequest;
import com.codigo.sanidaddivina.response.GoogleAuthResponse;
import com.codigo.sanidaddivina.response.SignUpResponse;

public interface GoogleAuthService {

    /** Verifica el ID token de Google y devuelve APPROVED / PENDING / NEW */
    GoogleAuthResponse loginWithGoogle(String idToken);

    /** Registra un nuevo usuario que ingresó con Google (completa su perfil) */
    SignUpResponse signupWithGoogle(GoogleSignUpRequest request);
}
