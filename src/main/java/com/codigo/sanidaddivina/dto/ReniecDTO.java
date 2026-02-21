package com.codigo.sanidaddivina.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReniecDTO {

        @JsonProperty("first_name")
        private String nombres;

        @JsonProperty("first_last_name")
        private String apellidoPaterno;

        @JsonProperty("second_last_name")
        private String apellidoMaterno;

        @JsonProperty("document_number")
        private String numeroDocumento;

        private String tipoDocumento;
        private String digitoVerificador;
}
