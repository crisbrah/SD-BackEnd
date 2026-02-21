package com.codigo.sanidaddivina.controller;

import com.codigo.sanidaddivina.request.SignInRequest;
import com.codigo.sanidaddivina.response.AuthenticationResponse;
import com.codigo.sanidaddivina.seguridad.AuthenticationService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
public class AutenticacionController {


    private final AuthenticationService authenticationService;

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signin(@RequestBody SignInRequest signInRequest){
        return ResponseEntity.ok(authenticationService.signin(signInRequest));
    }


}
