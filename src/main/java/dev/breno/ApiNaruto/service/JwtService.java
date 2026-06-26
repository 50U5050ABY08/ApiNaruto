/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.service;

import dev.breno.ApiNaruto.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * ============================================================================
 * SERVIÇO DE JWT
 * ============================================================================
 *
 * Responsável por gerar tokens JWT para autenticação.
 *
 * Traduções:
 *
 * JWT = JSON Web Token
 * Secret Key = Chave Secreta
 * Claims = Informações armazenadas no token
 */
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;

    public String gerarToken(String username) {

        Date agora = new Date();

        Date expiracao = Date.from(
                Instant.now().plus(
                        jwtProperties.getExpiration()
                )
        );

        return Jwts.builder()
                .subject(username)
                .issuedAt(agora)
                .expiration(expiracao)
                .signWith(obterChave())
                .compact();
    }

    public String extrairUsername(String token) {
        return extrairClaims(token).getSubject();
    }

    public boolean tokenValido(
            String token,
            String username) {

        String usernameToken =
                extrairUsername(token);

        return usernameToken.equals(username)
                && !tokenExpirado(token);
    }

    private boolean tokenExpirado(String token) {

        return extrairClaims(token)
                .getExpiration()
                .before(new Date());
    }

    private Claims extrairClaims(String token) {

        return Jwts.parser()
                .verifyWith(obterChave())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey obterChave() {

        return Keys.hmacShaKeyFor(
                jwtProperties
                        .getSecret()
                        .getBytes(StandardCharsets.UTF_8)
        );
    }
}