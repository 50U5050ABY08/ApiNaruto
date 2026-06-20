/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
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
public class JwtService {

    /**
     * Chave secreta utilizada para assinar os tokens.
     *
     * Em produção essa chave ficará em variáveis de ambiente.
     */
    private static final String SECRET_KEY =
            "minha-chave-secreta-super-segura-para-api-naruto-2026";

    public String gerarToken(String username) {

        SecretKey key = Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8)
        );

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                + 1000 * 60 * 60
                        )
                )
                .signWith(key)
                .compact();
    }
    
    /**
 * Extrai o username (subject) do token.
     * @param token
     * @return 
 */
public String extrairUsername(String token) {

    return extrairClaims(token).getSubject();
}

/**
 * Extrai todas as claims do token.
 */
private Claims extrairClaims(String token) {

    SecretKey key = Keys.hmacShaKeyFor(
            SECRET_KEY.getBytes(StandardCharsets.UTF_8)
        );

        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

        /**
 * Verifica se o token pertence ao usuário informado
 * e se ainda não expirou.
     * @param token
     * @param username
     * @return 
 */
public boolean tokenValido(
        String token,
        String username) {

    String usernameToken = extrairUsername(token);

        return usernameToken.equals(username)
            && !tokenExpirado(token);
    }

        /**
 * Verifica se o token já expirou.
 */
private boolean tokenExpirado(String token) {

        return extrairClaims(token)
            .getExpiration()
            .before(new Date());
    }
}
