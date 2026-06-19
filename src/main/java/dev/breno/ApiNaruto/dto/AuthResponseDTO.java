/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * ============================================================================
 * DTO DE RESPOSTA DE AUTENTICAÇÃO
 * ============================================================================
 *
 * Representa a resposta enviada ao cliente após o login.
 *
 * Futuramente esta classe carregará o token JWT.
 *
 * Tradução:
 *
 * Response = resposta
 * Token = credencial de acesso
 */
@Getter
@Setter
@AllArgsConstructor
public class AuthResponseDTO {

    private String token;
}