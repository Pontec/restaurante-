package com.pioriko.ms_restaurante.service.impl;

import com.pioriko.ms_restaurante.entities.EmpleadosEntity;
import com.pioriko.ms_restaurante.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    private final EmpleadoServiceImpl empleadoServiceImpl;

    @Value("${key.signature}")
    private String keySignature;

    public JwtServiceImpl(EmpleadoServiceImpl empleadoServiceImpl) {
        this.empleadoServiceImpl = empleadoServiceImpl;
    }

    @Override
    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    ///Generar un TOKEN
    @Override
    public String generateToken(UserDetails userDetails) {
        EmpleadosEntity empleado = (EmpleadosEntity) userDetails;
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", empleado.getAuthorities())
                .claim("nombre", empleado.getNombres())
                .claim("id", empleado.getId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 6000000))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }



    //Metodo que te devuelve la clave con la que se firma el token.
    private Key getSignKey(){
        byte[] key = Decoders.BASE64.decode(keySignature);
        return Keys.hmacShaKeyFor(key);
    }
    //Metodo para extraer el Payload del token, requiere firmarse para poder acceder al contenido.
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    //Metodo que te devuelve un objeto del body o tambien denomidado un Claim
    private <T> T extractClaims(String token, Function<Claims,T> claimResult){
        final Claims claims = extractAllClaims(token);
        return claimResult.apply(claims);
    }

    //Metodo para validar el token
    private boolean isTokenExpired(String token){
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }


}
