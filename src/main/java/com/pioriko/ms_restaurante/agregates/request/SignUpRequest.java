package com.pioriko.ms_restaurante.agregates.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String nombres;
    private String apellidos;
    private String email;
    private String password;
    private String numDoc;
    private String direccion;
    private String telefono;

}
