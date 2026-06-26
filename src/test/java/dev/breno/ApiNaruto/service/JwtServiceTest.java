/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.service;

import dev.breno.ApiNaruto.service.JwtService;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dev.breno.ApiNaruto.config.JwtProperties;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void prepararTeste() {

        JwtProperties jwtProperties =
                new JwtProperties();

        jwtProperties.setSecret(
                "chave-exclusiva-de-testes-com-tamanho-seguro-12345678901234567890"
        );

        jwtProperties.setExpiration(
            Duration.ofHours(1)
        );

        jwtService = new JwtService(jwtProperties);
    }

    @Test
    void deveGerarTokenEExtrairUsername() {

        String token = jwtService.gerarToken("breno");

        assertThat(token).isNotBlank();

        String usernameExtraido =
                jwtService.extrairUsername(token);

        assertThat(usernameExtraido)
                .isEqualTo("breno");
    }

    @Test
    void deveConsiderarTokenValidoParaMesmoUsuario() {

        String token = jwtService.gerarToken("breno");

        boolean resultado =
                jwtService.tokenValido(token, "breno");

        assertThat(resultado).isTrue();
    }

    @Test
    void deveConsiderarTokenInvalidoParaOutroUsuario() {

        String token = jwtService.gerarToken("breno");

        boolean resultado =
                jwtService.tokenValido(token, "invasor");

        assertThat(resultado).isFalse();
    }

    @Test
    void deveRejeitarTokenComAssinaturaAlterada() {

        String tokenOriginal =
                jwtService.gerarToken("breno");

        int inicioAssinatura =
                tokenOriginal.lastIndexOf('.') + 1;

        char caractereOriginal =
                tokenOriginal.charAt(inicioAssinatura);

        char novoCaractere =
                caractereOriginal == 'a' ? 'b' : 'a';

        String tokenAlterado =
                tokenOriginal.substring(0, inicioAssinatura)
                + novoCaractere
                + tokenOriginal.substring(inicioAssinatura + 1);

        assertThatThrownBy(
                () -> jwtService.extrairUsername(tokenAlterado)
        ).isInstanceOf(JwtException.class);
    }
}