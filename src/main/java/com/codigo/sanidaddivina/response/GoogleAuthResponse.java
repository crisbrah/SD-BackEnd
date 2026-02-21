package com.codigo.sanidaddivina.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GoogleAuthResponse {
    /** "APPROVED" | "PENDING" | "NEW" */
    private String status;

    // Cuando status = APPROVED
    private String token;
    private String refreshToken;

    // Cuando status = NEW (para pre-rellenar el formulario de registro)
    private String email;
    private String nombres;
}
