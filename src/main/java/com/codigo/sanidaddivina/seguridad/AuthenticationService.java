package com.codigo.sanidaddivina.seguridad;


import com.codigo.sanidaddivina.request.SignInRequest;
import com.codigo.sanidaddivina.request.SignUpRequest;
import com.codigo.sanidaddivina.response.AuthenticationResponse;
import com.codigo.sanidaddivina.response.SignUpResponse;

public interface AuthenticationService {

    AuthenticationResponse signin(SignInRequest signInRequest);

    SignUpResponse signup(SignUpRequest request);
}
