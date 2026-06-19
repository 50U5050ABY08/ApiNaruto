/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * ============================================================================
 * DTO DE REQUISIÇÃO DE AUTENTICAÇÃO
 * ============================================================================
 *
 * Representa os dados enviados pelo cliente para realizar login.
 *
 * Tradução:
 *
 * Auth = autenticação
 * Request = requisição
 */
@Getter
@Setter
public class AuthRequestDTO {

    @NotBlank(message = "O username é obrigatório")
    private String username;

    @NotBlank(message = "A senha é obrigatória")
    private String password;
}