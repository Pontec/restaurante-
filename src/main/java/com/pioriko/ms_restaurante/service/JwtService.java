package com.pioriko.ms_restaurante.service;

import com.pioriko.ms_restaurante.entities.Empleados;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String extractUserName(String token);
    String generateToken(UserDetails userDetails);
    boolean validateToken(String token, UserDetails userDetails);

}
