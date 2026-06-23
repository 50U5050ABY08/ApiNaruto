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
 * DTO DE CADASTRO DE USUÁRIO
 * ============================================================================
 *
 * Representa os dados enviados pelo cliente para criar um usuário do sistema.
 *
 * Traduções:
 *
 * Register = cadastrar
 * User = usuário
 * Password = senha
 */
@Getter
@Setter
public class UserRequestDTO {

    @NotBlank(message = "O username é obrigatório")
    private String username;

    @NotBlank(message = "A senha é obrigatória")
    private String password;
}